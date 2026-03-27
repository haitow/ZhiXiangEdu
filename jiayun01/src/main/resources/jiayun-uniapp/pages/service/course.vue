<template>
	<view class="course-container">
		<view v-if="userType=='会员'" class="member-view">
			<view class="header-section">
				<view class="header-bg"></view>
				<view class="header-content">
					<view class="header-title">预约课程</view>
					<view class="header-subtitle">选择适合你的课程</view>
				</view>
			</view>
			
			<view class="tabs-section">
				<scroll-view scroll-x class="tabs-scroll">
					<view class="tabs-wrapper">
						<view 
							class="tab-item" 
							:class="{'active': current === index}" 
							v-for="(item, index) in items" 
							:key="index"
							@click="onClickItem({currentIndex: index})"
						>
							{{item}}
						</view>
					</view>
				</scroll-view>
			</view>
			
			<view class="course-list">
				<view class="course-card" v-for="item in leagueClass" :key="item.id">
					<view class="course-image">
						<image src="/static/img/practice.png" mode="aspectFill"></image>
					</view>
					<view class="course-info">
						<view class="course-name">{{item.name}}</view>
						<view class="course-tags">
							<text class="tag" v-for="(tag, index) in item.tags" :key="index">{{tag}}</text>
						</view>
						<view class="course-desc" v-if="item.description">{{item.description}}</view>
						<view class="course-footer">
							<view class="course-price" v-if="item.price">
								<text class="price-label">¥</text>
								<text class="price-value">{{item.price}}</text>
							</view>
							<view class="choose-btn" @click="chooseClasses(item)">
								立即预约
							</view>
						</view>
					</view>
				</view>
				
				<view class="empty-state" v-if="leagueClass.length === 0">
					<image src="/static/img/practice.png" class="empty-icon"></image>
					<text class="empty-text">暂无课程</text>
				</view>
			</view>
		</view>
		
		<view v-else class="teacher-view">
			<view class="header-section">
				<view class="header-bg"></view>
				<view class="header-content">
					<view class="header-title">我的课程</view>
					<view class="header-subtitle">管理已开设的课程</view>
				</view>
			</view>
			
			<view class="teacher-actions">
				<view class="add-btn" @click="openDialog()">
					<text class="add-icon">+</text>
					<text class="add-text">新建课程</text>
				</view>
			</view>
			
			<view class="class-list">
				<view class="class-card" v-for="item in classesList" :key="item.id">
					<view class="class-time">
						<view class="time-icon">
							<image src="/static/img/activity.png"></image>
						</view>
						<view class="time-text">{{item.endTime}}</view>
					</view>
					<view class="class-content">
						<view class="class-image">
							<image :src="item.imgs" mode="aspectFill"></image>
						</view>
						<view class="class-info">
							<view class="class-name">{{item.name}}</view>
							<view class="class-room">
								<image src="/static/img/map.png" class="room-icon"></image>
								<text>{{item.roomName}}</text>
							</view>
						</view>
						<view class="class-action" @click="navToStudentList(item.id)">
							<text>查看会员</text>
						</view>
					</view>
				</view>
				
				<view class="empty-state" v-if="classesList.length === 0">
					<image src="/static/img/practice.png" class="empty-icon"></image>
					<text class="empty-text">暂无课程</text>
				</view>
			</view>
		</view>
		
		<uni-popup ref="inputDialog" type="dialog">
			<view class="dialog-container">
				<view class="dialog-header">
					<view class="header-left">
						<view class="header-icon">
							<image src="/static/img/practice.png"></image>
						</view>
						<text class="dialog-title">新建课程</text>
					</view>
					<view class="dialog-close" @click="$refs.inputDialog.close()">
						<text>×</text>
					</view>
				</view>
				<scroll-view scroll-y class="dialog-content">
					<view class="form-section">
						<view class="section-title">基本信息</view>
						<view class="form-item">
							<text class="form-label">课程名称</text>
							<input class="form-input" v-model="baseFormData.name" placeholder="请输入课程名称" />
						</view>
						<view class="form-row">
							<view class="form-item half">
								<text class="form-label">课程价格</text>
								<input class="form-input" v-model="baseFormData.price" placeholder="请输入价格" type="number" />
							</view>
							<view class="form-item half">
								<text class="form-label">上限人数</text>
								<input class="form-input" v-model="baseFormData.pmax" placeholder="请输入人数" type="number" />
							</view>
						</view>
					</view>
					
					<view class="form-section">
						<view class="section-title">课程标签</view>
						<view class="form-item">
							<view class="tags-select">
								<view 
									class="tag-option" 
									:class="{'selected': baseFormData.tags && baseFormData.tags.includes(item.value)}"
									v-for="(item, index) in tagsList" 
									:key="index"
									@click="toggleTag(item.value)"
								>
									<text class="tag-check" v-if="baseFormData.tags && baseFormData.tags.includes(item.value)">✓</text>
									{{item.text}}
								</view>
							</view>
						</view>
					</view>
					
					<view class="form-section">
						<view class="section-title">时间地点</view>
						<view class="form-item">
							<text class="form-label">教室名称</text>
							<picker mode="selector" :range="roomList" range-key="text" @change="onRoomChange">
								<view class="form-picker">
									<text v-if="baseFormData.roomName">{{baseFormData.roomName}}</text>
									<text v-else class="placeholder">请选择教室</text>
									<text class="picker-arrow">›</text>
								</view>
							</picker>
						</view>
						<view class="form-row">
							<view class="form-item half">
								<text class="form-label">开课时间</text>
								<picker mode="multiSelector" :range="dateTimeRange" :value="startTimeValue" @change="onStartTimeChange" @columnchange="onColumnChange">
									<view class="form-picker">
										<text v-if="baseFormData.startTime" class="time-text">{{baseFormData.startTime.substring(11, 16)}}</text>
										<text v-else class="placeholder">选择时间</text>
										<text class="picker-arrow">›</text>
									</view>
								</picker>
							</view>
							<view class="form-item half">
								<text class="form-label">结束时间</text>
								<picker mode="multiSelector" :range="dateTimeRange" :value="endTimeValue" @change="onEndTimeChange" @columnchange="onColumnChange">
									<view class="form-picker">
										<text v-if="baseFormData.endTime" class="time-text">{{baseFormData.endTime.substring(11, 16)}}</text>
										<text v-else class="placeholder">选择时间</text>
										<text class="picker-arrow">›</text>
									</view>
								</picker>
							</view>
						</view>
					</view>
					
					<view class="form-section">
						<view class="section-title">课程封面</view>
						<view class="form-item">
							<view class="upload-area" @click="upload()">
								<image v-if="imageUrl" :src="imageUrl" class="preview-image"></image>
								<view v-else class="upload-placeholder">
									<text class="upload-icon">+</text>
									<text class="upload-text">上传课程封面</text>
								</view>
							</view>
						</view>
					</view>
					
					<view class="form-section">
						<view class="section-title">课程描述</view>
						<view class="form-item">
							<textarea class="form-textarea" v-model="baseFormData.description" placeholder="请输入课程描述（选填）" maxlength="200"></textarea>
						</view>
					</view>
				</scroll-view>
				<view class="dialog-footer">
					<view class="cancel-btn" @click="$refs.inputDialog.close()">取消</view>
					<view class="confirm-btn" @click="dialogInputConfirm">创建课程</view>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import {
		addClasses,
		getClassList
	} from '../../api/class'
	import { getRoomList } from '../../api/room'
	import {
		getTagsList
	} from '../../api/tags'
	import {
		chooseClasses,
		getClassByTeacherId,
		getUserById
	} from '../../api/user'
	import {
		baseUrl
	} from '../../config.js'
	
	export default {
		data() {
			return {
				current: 0,
				items: [],
				userType: "",
				leagueClass: [],
				classesList: [],
				tagsList: [],
				baseFormData: {
					tags: []
				},
				roomList: [],
				imageUrl: "",
				imageName: "",
				dateTimeRange: [[], [], []],
				startTimeValue: [0, 0, 0],
				endTimeValue: [0, 0, 0]
			}
		},
		methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
					getClassList(this.items[this.current]).then(res => {
						for (var i = 0; i < res.data.data.length; i++) {
							var tags = res.data.data[i].tags.split(",")
							res.data.data[i].tags = tags
						}
						this.leagueClass = res.data.data
					})
				}
			},
			chooseClasses(e) {
				var params = {
					classId: e.id,
					userId: uni.getStorageSync('auth')
				}
				uni.showModal({
					title: "确认预约",
					content: "是否选择此课程？",
					success: (res) => {
						if (res.confirm) {
							chooseClasses(params).then(res => {
								if (res.data.code == 0) {
									uni.showToast({
										title: res.data.msg,
										duration: 2000,
										icon: "error"
									})
								} else {
									uni.showToast({
										title: res.data.data,
										duration: 2000
									})
								}
							})
						}
					}
				})
			},
			navToStudentList(id) {
				uni.navigateTo({
					url: "/pages/detail/studentDetail?id=" + id
				})
			},
			openDialog() {
				this.baseFormData = { tags: [] }
				this.imageUrl = ""
				this.imageName = ""
				this.startTimeValue = [0, 0, 0]
				this.endTimeValue = [0, 0, 0]
				this.$refs.inputDialog.open()
			},
			toggleTag(value) {
				if (!this.baseFormData.tags) {
					this.baseFormData.tags = []
				}
				const index = this.baseFormData.tags.indexOf(value)
				if (index > -1) {
					this.baseFormData.tags.splice(index, 1)
				} else {
					this.baseFormData.tags.push(value)
				}
				this.$forceUpdate()
			},
			onRoomChange(e) {
				const index = e.detail.value
				this.baseFormData.roomName = this.roomList[index].value
				this.$forceUpdate()
			},
			onStartTimeChange(e) {
				const values = e.detail.value
				this.startTimeValue = values
				this.baseFormData.startTime = this.formatDateTime(values)
				this.$forceUpdate()
			},
			onEndTimeChange(e) {
				const values = e.detail.value
				this.endTimeValue = values
				this.baseFormData.endTime = this.formatDateTime(values)
				this.$forceUpdate()
			},
			formatDateTime(values) {
				const date = this.dateTimeRange[0][values[0]]
				const hour = this.dateTimeRange[1][values[1]]
				const minute = this.dateTimeRange[2][values[2]]
				return `${date} ${hour}:${minute}:00`
			},
			onColumnChange(e) {
				
			},
			dialogInputConfirm() {
				var tags = ""
				if (this.baseFormData.tags && this.baseFormData.tags.length > 0) {
					tags = this.baseFormData.tags.join(",")
				}
				var params = {
					name: this.baseFormData.name,
					price: this.baseFormData.price,
					pmax: this.baseFormData.pmax,
					roomName: this.baseFormData.roomName,
					startTime: this.baseFormData.startTime,
					endTime: this.baseFormData.endTime,
					description: this.baseFormData.description,
					teacherId: uni.getStorageSync('auth'),
					tags: tags,
					imgs: this.imageName
				}
				getUserById(uni.getStorageSync('auth')).then(r => {
					params.tname = r.data.data.name
					addClasses(params).then(res => {
						if (res.data.code == 1) {
							uni.showToast({
								title: '创建成功',
								icon: 'success'
							})
							this.$refs.inputDialog.close()
							this.init()
						} else {
							uni.showToast({
								title: res.data.msg || '创建失败',
								icon: 'error'
							})
						}
					})
				})
			},
			upload() {
				var _this = this
				wx.chooseMessageFile({
					count: 1,
					type: 'file',
					success(res) {
						const tempFilePaths = res.tempFiles[0].path
						let filename = res.tempFiles[0].name
						wx.uploadFile({
							url: baseUrl + '/common/upload',
							filePath: tempFilePaths,
							name: 'file',
							fileName: filename,
							success(res1) {
								_this.imageName = JSON.parse(res1.data).data
								_this.imageUrl = baseUrl + "/common/download?name=" + JSON.parse(res1.data).data
							}
						})
					}
				})
			},
			initDateTimeRange() {
				const dates = []
				const hours = []
				const minutes = []
				
				for (let i = 0; i < 30; i++) {
					const date = new Date()
					date.setDate(date.getDate() + i)
					dates.push(`${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`)
				}
				
				for (let i = 6; i <= 22; i++) {
					hours.push(String(i).padStart(2, '0'))
				}
				
				for (let i = 0; i < 60; i += 5) {
					minutes.push(String(i).padStart(2, '0'))
				}
				
				this.dateTimeRange = [dates, hours, minutes]
			},
			init() {
				var userId = uni.getStorageSync('auth')
				var _this = this
				getUserById(userId).then(res => {
					this.userType = res.data.data.type
					if (this.userType == '老师') {
						getRoomList().then(res => {
							this.roomList = []
							if (res.data.code == 1 && res.data.data) {
								for (var i = 0; i < res.data.data.length; i++) {
									var params = {
										text: res.data.data[i],
										value: res.data.data[i]
									}
									this.roomList.push(params)
								}
							}
						})
						getTagsList().then(res => {
							this.tagsList = []
							if (res.data.code == 1 && res.data.data) {
								for (var i = 0; i < res.data.data.length; i++) {
									var params = {
										text: res.data.data[i].name,
										value: res.data.data[i].name
									}
									this.tagsList.push(params)
								}
							}
						})
						getClassByTeacherId(userId).then(res => {
							for (var i = 0; i < res.data.data.length; i++) {
								res.data.data[i].imgs = baseUrl + "/common/download?name=" + res.data.data[i].imgs
							}
							this.classesList = res.data.data
						})
					} else {
						getTagsList().then(res => {
							for (var i = 0; i < res.data.data.length; i++) {
								this.items[i] = res.data.data[i].name
							}
							if (res.data.code == 1) {
								getClassList(this.items[this.current]).then(res => {
									for (var i = 0; i < res.data.data.length; i++) {
										var tags = res.data.data[i].tags.split(",")
										res.data.data[i].tags = tags
									}
									_this.leagueClass = res.data.data
								})
							}
						})
					}
				})
			}
		},
		onLoad() {
			this.initDateTimeRange()
			this.init()
		},
		onPullDownRefresh() {
			this.init()
			setTimeout(() => {
				uni.stopPullDownRefresh()
			}, 500)
		}
	}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.course-container {
	min-height: 100vh;
}

.header-section {
	position: relative;
	margin-bottom: 24rpx;
}

.header-bg {
	height: 200rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 0 0 40rpx 40rpx;
}

.header-content {
	position: relative;
	padding: 40rpx 32rpx;
	margin-top: -160rpx;
}

.header-title {
	font-size: 40rpx;
	font-weight: bold;
	color: #fff;
	margin-bottom: 8rpx;
}

.header-subtitle {
	font-size: 26rpx;
	color: rgba(255, 255, 255, 0.9);
}

.tabs-section {
	background: #fff;
	padding: 20rpx 0;
	margin-bottom: 20rpx;
}

.tabs-scroll {
	white-space: nowrap;
}

.tabs-wrapper {
	display: inline-flex;
	padding: 0 24rpx;
}

.tab-item {
	display: inline-block;
	padding: 16rpx 32rpx;
	margin-right: 20rpx;
	font-size: 28rpx;
	color: #64748B;
	background: #F1F5F9;
	border-radius: 30rpx;
	transition: all 0.3s;
}

.tab-item.active {
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
}

.course-list {
	padding: 0 24rpx 30rpx;
}

.course-card {
	background: #fff;
	border-radius: 20rpx;
	margin-bottom: 24rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.course-image {
	width: 100%;
	height: 280rpx;
}

.course-image image {
	width: 100%;
	height: 100%;
}

.course-info {
	padding: 24rpx;
}

.course-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 16rpx;
}

.course-tags {
	display: flex;
	flex-wrap: wrap;
	margin-bottom: 16rpx;
}

.tag {
	display: inline-block;
	padding: 8rpx 16rpx;
	margin-right: 12rpx;
	margin-bottom: 8rpx;
	font-size: 22rpx;
	color: #3B82F6;
	background: rgba(59, 130, 246, 0.1);
	border-radius: 20rpx;
}

.course-desc {
	font-size: 26rpx;
	color: #64748B;
	margin-bottom: 20rpx;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}

.course-footer {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding-top: 16rpx;
	border-top: 1rpx solid #F1F5F9;
}

.course-price {
	color: #F97316;
}

.price-label {
	font-size: 24rpx;
}

.price-value {
	font-size: 36rpx;
	font-weight: bold;
}

.choose-btn {
	padding: 16rpx 40rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
	font-size: 28rpx;
	border-radius: 30rpx;
}

.teacher-actions {
	padding: 0 24rpx 20rpx;
}

.add-btn {
	display: flex;
	align-items: center;
	justify-content: center;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
	padding: 24rpx;
	border-radius: 20rpx;
	box-shadow: 0 8rpx 24rpx rgba(59, 130, 246, 0.3);
}

.add-icon {
	font-size: 40rpx;
	margin-right: 12rpx;
}

.add-text {
	font-size: 30rpx;
}

.class-list {
	padding: 0 24rpx 30rpx;
}

.class-card {
	background: #fff;
	border-radius: 20rpx;
	margin-bottom: 24rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.class-time {
	display: flex;
	align-items: center;
	padding: 20rpx 24rpx;
	background: linear-gradient(135deg, #EFF6FF 0%, #F8FAFC 100%);
}

.time-icon {
	width: 36rpx;
	height: 36rpx;
	margin-right: 12rpx;
}

.time-icon image {
	width: 100%;
	height: 100%;
}

.time-text {
	font-size: 26rpx;
	color: #3B82F6;
	font-weight: 500;
}

.class-content {
	display: flex;
	padding: 24rpx;
}

.class-image {
	width: 160rpx;
	height: 120rpx;
	border-radius: 12rpx;
	overflow: hidden;
	margin-right: 20rpx;
}

.class-image image {
	width: 100%;
	height: 100%;
}

.class-info {
	flex: 1;
}

.class-name {
	font-size: 30rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 12rpx;
}

.class-room {
	display: flex;
	align-items: center;
	font-size: 26rpx;
	color: #64748B;
}

.room-icon {
	width: 28rpx;
	height: 28rpx;
	margin-right: 8rpx;
}

.class-action {
	display: flex;
	align-items: center;
	padding: 16rpx 24rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
	font-size: 26rpx;
	border-radius: 30rpx;
	align-self: center;
}

.empty-state {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 100rpx 0;
}

.empty-icon {
	width: 160rpx;
	height: 160rpx;
	opacity: 0.5;
	margin-bottom: 24rpx;
}

.empty-text {
	font-size: 28rpx;
	color: #999;
}

.dialog-container {
	width: 680rpx;
	max-height: 85vh;
	background: #fff;
	border-radius: 28rpx;
	overflow: hidden;
}

.dialog-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 36rpx 32rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
}

.header-left {
	display: flex;
	align-items: center;
}

.header-icon {
	width: 48rpx;
	height: 48rpx;
	margin-right: 16rpx;
}

.header-icon image {
	width: 100%;
	height: 100%;
}

.dialog-title {
	font-size: 36rpx;
	font-weight: 600;
	color: #fff;
}

.dialog-close {
	width: 56rpx;
	height: 56rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	background: rgba(255, 255, 255, 0.2);
	border-radius: 50%;
}

.dialog-close text {
	font-size: 40rpx;
	color: #fff;
	line-height: 1;
}

.dialog-content {
	max-height: 65vh;
	padding: 0;
}

.form-section {
	padding: 24rpx 32rpx;
	border-bottom: 16rpx solid #f5f6fa;
}

.form-section:last-child {
	border-bottom: none;
}

.section-title {
	font-size: 28rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 24rpx;
	padding-left: 16rpx;
	border-left: 6rpx solid #3B82F6;
}

.form-item {
	margin-bottom: 24rpx;
}

.form-item:last-child {
	margin-bottom: 0;
}

.form-label {
	display: block;
	font-size: 26rpx;
	color: #64748B;
	margin-bottom: 12rpx;
}

.form-row {
	display: flex;
	margin: 0 -12rpx;
}

.form-item.half {
	flex: 1;
	margin: 0 12rpx;
}

.form-input {
	width: 100%;
	height: 88rpx;
	padding: 0 24rpx;
	background: #F8FAFC;
	border-radius: 16rpx;
	font-size: 28rpx;
	box-sizing: border-box;
	border: 2rpx solid transparent;
	transition: all 0.3s;
}

.form-input:focus {
	background: #fff;
	border-color: #3B82F6;
}

.form-picker {
	display: flex;
	justify-content: space-between;
	align-items: center;
	height: 88rpx;
	padding: 0 24rpx;
	background: #F8FAFC;
	border-radius: 16rpx;
	font-size: 28rpx;
	border: 2rpx solid transparent;
	transition: all 0.3s;
}

.time-text {
	color: #3B82F6;
	font-weight: 500;
}

.placeholder {
	color: #aaa;
}

.picker-arrow {
	font-size: 36rpx;
	color: #ccc;
}

.tags-select {
	display: flex;
	flex-wrap: wrap;
	margin: -8rpx;
}

.tag-option {
	display: flex;
	align-items: center;
	padding: 16rpx 24rpx;
	margin: 8rpx;
	background: #f8f9fa;
	border-radius: 30rpx;
	font-size: 26rpx;
	color: #666;
	transition: all 0.3s;
	border: 2rpx solid transparent;
}

.tag-option.selected {
	background: linear-gradient(135deg, #EFF6FF 0%, #F8FAFC 100%);
	color: #3B82F6;
	border-color: #3B82F6;
}

.tag-check {
	margin-right: 8rpx;
	font-weight: bold;
}

.upload-area {
	width: 100%;
	height: 240rpx;
	border-radius: 16rpx;
	overflow: hidden;
}

.preview-image {
	width: 100%;
	height: 100%;
}

.upload-placeholder {
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	background: #f8f9fa;
	border: 3rpx dashed #ddd;
	border-radius: 16rpx;
	transition: all 0.3s;
}

.upload-placeholder:active {
	background: #f0f0f0;
}

.upload-icon {
	font-size: 72rpx;
	color: #ccc;
	margin-bottom: 12rpx;
}

.upload-text {
	font-size: 26rpx;
	color: #999;
}

.form-textarea {
	width: 100%;
	height: 180rpx;
	padding: 20rpx;
	background: #f8f9fa;
	border-radius: 16rpx;
	font-size: 28rpx;
	box-sizing: border-box;
	border: 2rpx solid transparent;
	transition: all 0.3s;
}

.form-textarea:focus {
	background: #fff;
	border-color: #3B82F6;
}

.dialog-footer {
	display: flex;
	padding: 24rpx 32rpx 32rpx;
	background: #fff;
}

.cancel-btn {
	flex: 1;
	height: 96rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	background: #F1F5F9;
	color: #64748B;
	font-size: 30rpx;
	border-radius: 16rpx;
	margin-right: 20rpx;
	transition: all 0.3s;
}

.cancel-btn:active {
	background: #E2E8F0;
}

.confirm-btn {
	flex: 1;
	height: 96rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
	font-size: 30rpx;
	font-weight: 500;
	border-radius: 16rpx;
	box-shadow: 0 8rpx 24rpx rgba(59, 130, 246, 0.3);
	transition: all 0.3s;
}

.confirm-btn:active {
	transform: scale(0.98);
}
</style>
