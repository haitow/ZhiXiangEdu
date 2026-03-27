<template>
	<view class="student-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">课程会员</view>
				<view class="header-subtitle">查看已选课会员列表</view>
			</view>
		</view>
		
		<view class="student-list">
			<view class="student-card" v-for="item in studentList" :key="item.id">
				<view class="student-avatar">
					<image :src="item.image" mode="aspectFill"></image>
				</view>
				<view class="student-info">
					<view class="student-name">{{item.name}}</view>
					<view class="student-phone">{{item.phone}}</view>
				</view>
				<view class="student-action">
					<view 
						class="status-btn arrived" 
						v-if="item.status == 1" 
						@click="absence(item.id, item.status)"
					>
						已到
					</view>
					<view 
						class="status-btn absent" 
						v-else-if="item.status == 2" 
						@click="absence(item.id, item.status)"
					>
						未到
					</view>
					<view 
						class="status-btn pending" 
						v-else 
						@click="absence(item.id, item.status)"
					>
						待签到
					</view>
				</view>
			</view>
			
			<view class="empty-state" v-if="studentList.length === 0">
				<image src="/static/img/practice.png" class="empty-icon"></image>
				<text class="empty-text">暂无选课会员</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getStudentByClassId
	} from '../../api/class';
	import {
		userAttendance
	} from '../../api/user'
	import {
		baseUrl
	} from '../../config';
	
	export default {
		data() {
			return {
				studentList: [],
				classId: ""
			}
		},
		methods: {
			loadStudents() {
				getStudentByClassId(this.classId).then(res => {
					if (res.data.code == 1 && res.data.data) {
						for (var i = 0; i < res.data.data.length; i++) {
							res.data.data[i].image = baseUrl + "/common/download?name=" + res.data.data[i].image
						}
						this.studentList = res.data.data
					} else {
						this.studentList = []
					}
				}).catch(() => {
					this.studentList = []
				})
			},
			absence(id, status) {
				var _this = this
				var classId = this.classId
				if (status == 1) {
					uni.showModal({
						title: "提示",
						content: "确认该会员缺勤？",
						success(result) {
							if (result.confirm) {
								userAttendance(classId, id, 2).then(res => {
									uni.showToast({
										title: "操作成功",
										icon: "success"
									})
									_this.loadStudents()
								})
							}
						}
					})
				} else {
					uni.showModal({
						title: "提示",
						content: "确认该会员已到？",
						success(result) {
							if (result.confirm) {
								userAttendance(classId, id, 1).then(res => {
									if (res.data.code == 1) {
										uni.showToast({
											title: "操作成功",
											icon: "success"
										})
										_this.loadStudents()
									}
								})
							}
						}
					})
				}
			}
		},
		onLoad(options) {
			this.classId = options.id
			this.loadStudents()
		},
		onPullDownRefresh() {
			this.loadStudents()
			setTimeout(() => {
				uni.stopPullDownRefresh()
			}, 500)
		}
	}
</script>

<style lang="scss">
page {
	background-color: #f5f6fa;
}

.student-container {
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

.student-list {
	padding: 0 24rpx 30rpx;
}

.student-card {
	display: flex;
	align-items: center;
	background: #fff;
	border-radius: 20rpx;
	padding: 24rpx;
	margin-bottom: 20rpx;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.student-avatar {
	width: 100rpx;
	height: 100rpx;
	border-radius: 50%;
	overflow: hidden;
	flex-shrink: 0;
	border: 4rpx solid #e3f2fd;
}

.student-avatar image {
	width: 100%;
	height: 100%;
}

.student-info {
	flex: 1;
	margin-left: 24rpx;
}

.student-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #333;
	margin-bottom: 8rpx;
}

.student-phone {
	font-size: 26rpx;
	color: #999;
}

.student-action {
	flex-shrink: 0;
}

.status-btn {
	padding: 16rpx 32rpx;
	border-radius: 30rpx;
	font-size: 26rpx;
	font-weight: 500;
}

.arrived {
	background: #e8f5e9;
	color: #4caf50;
}

.absent {
	background: #ffebee;
	color: #f44336;
}

.pending {
	background: #fff3e0;
	color: #ff9800;
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
</style>
