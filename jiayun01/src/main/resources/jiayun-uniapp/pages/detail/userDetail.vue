<template>
	<view class="user-detail-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="back-btn" @click="back">
					<text class="back-icon">‹</text>
				</view>
				<text class="header-title">编辑资料</text>
			</view>
		</view>
		
		<view class="main-content">
			<view class="avatar-section" @click="pickHead">
				<view class="avatar-wrapper">
					<image class="avatar-image" :src="imageUrl" mode="aspectFill"></image>
					<view class="avatar-edit">
						<text>更换头像</text>
					</view>
				</view>
			</view>
			
			<view class="form-section">
				<view class="form-item">
					<text class="form-label">用户姓名</text>
					<input class="form-input" v-model="userDetail.name" placeholder="请输入用户姓名" />
				</view>
				<view class="form-item">
					<text class="form-label">联系方式</text>
					<input class="form-input" v-model="userDetail.phone" placeholder="请输入联系方式" />
				</view>
				<view class="form-item">
					<text class="form-label">性别</text>
					<view class="sex-options">
						<view class="sex-option" :class="{'active': userDetail.sex == 1}" @click="userDetail.sex = 1">
							<text>男</text>
						</view>
						<view class="sex-option" :class="{'active': userDetail.sex == 0}" @click="userDetail.sex = 0">
							<text>女</text>
						</view>
					</view>
				</view>
				<view class="form-item">
					<text class="form-label">生日</text>
					<picker mode="date" :value="userDetail.birthday" @change="onDateChange">
						<view class="form-picker">
							<text v-if="userDetail.birthday">{{userDetail.birthday}}</text>
							<text v-else class="placeholder">请选择生日</text>
							<text class="picker-arrow">›</text>
						</view>
					</picker>
				</view>
				<view class="form-item">
					<text class="form-label">身份证号码</text>
					<input class="form-input" v-model="userDetail.idNumber" placeholder="请输入身份证号码" />
				</view>
			</view>
			
			<view class="submit-btn" @click="submit">
				<text>确认修改</text>
			</view>
		</view>
	</view>
</template>

<script>
	import { getUserById, updateUser } from '../../api/user'
	import { baseUrl } from '../../config'
	
	export default {
		data() {
			return {
				userDetail: {},
				imageName: "",
				imageUrl: ""
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			onDateChange(e) {
				this.userDetail.birthday = e.detail.value
			},
			submit() {
				var params = { ...this.userDetail, image: this.imageName }
				updateUser(params).then(res => {
					this.init(uni.getStorageSync('auth'))
					if (res.data.code == 1) {
						uni.showToast({
							title: res.data.data,
							icon: "success"
						})
						setTimeout(() => {
							uni.navigateBack({
								delta: 1
							})
						}, 1500)
					} else {
						uni.showToast({
							title: "修改失败",
							icon: "error"
						})
					}
				})
			},
			init(id) {
				var _this = this
				getUserById(id).then(res => {
					_this.imageUrl = baseUrl + "/common/download?name=" + res.data.data.image
					_this.imageName = res.data.data.image
					_this.userDetail = res.data.data
				})
			},
			pickHead() {
				var _this = this
				wx.chooseMessageFile({
					count: 1,
					type: 'file',
					success(res) {
						const tempFilePaths = res.tempFiles[0].path
						let filename = res.tempFiles[0].name;
						
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
			}
		},
		onLoad() {
			var id = uni.getStorageSync('auth')
			this.init(id)
		}
	}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.user-detail-container {
	min-height: 100vh;
}

.header-section {
	position: relative;
	margin-bottom: 24rpx;
}

.header-bg {
	height: 200rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
}

.header-content {
	position: relative;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 0 32rpx;
	margin-top: -160rpx;
}

.back-btn {
	position: absolute;
	left: 32rpx;
	width: 64rpx;
	height: 64rpx;
	background: rgba(255, 255, 255, 0.2);
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
}

.back-icon {
	font-size: 40rpx;
	color: #fff;
}

.header-title {
	font-size: 36rpx;
	font-weight: 600;
	color: #fff;
}

.main-content {
	padding: 0 24rpx 30rpx;
}

.avatar-section {
	display: flex;
	justify-content: center;
	padding: 40rpx 0;
}

.avatar-wrapper {
	position: relative;
	width: 180rpx;
	height: 180rpx;
}

.avatar-image {
	width: 100%;
	height: 100%;
	border-radius: 50%;
	border: 6rpx solid #fff;
	box-shadow: 0 8rpx 32rpx rgba(59, 130, 246, 0.3);
}

.avatar-edit {
	position: absolute;
	bottom: 0;
	left: 0;
	right: 0;
	padding: 12rpx 0;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 0 0 90rpx 90rpx;
	text-align: center;
}

.avatar-edit text {
	font-size: 24rpx;
	color: #fff;
}

.form-section {
	background: #fff;
	border-radius: 20rpx;
	padding: 30rpx;
	margin-bottom: 24rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.form-item {
	display: flex;
	align-items: center;
	padding: 24rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.form-item:last-child {
	border-bottom: none;
}

.form-label {
	width: 180rpx;
	font-size: 28rpx;
	color: #64748B;
}

.form-input {
	flex: 1;
	font-size: 28rpx;
	color: #1E293B;
}

.form-picker {
	flex: 1;
	display: flex;
	justify-content: space-between;
	align-items: center;
	font-size: 28rpx;
	color: #1E293B;
}

.placeholder {
	color: #94A3B8;
}

.picker-arrow {
	font-size: 32rpx;
	color: #CBD5E1;
}

.sex-options {
	flex: 1;
	display: flex;
	gap: 20rpx;
}

.sex-option {
	padding: 16rpx 40rpx;
	background: #F1F5F9;
	border-radius: 8rpx;
	font-size: 28rpx;
	color: #64748B;
	transition: all 0.3s;
}

.sex-option.active {
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
}

.submit-btn {
	width: 100%;
	height: 96rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 16rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: 0 8rpx 24rpx rgba(59, 130, 246, 0.3);
	margin-top: 40rpx;
}

.submit-btn text {
	font-size: 32rpx;
	font-weight: 600;
	color: #fff;
	letter-spacing: 8rpx;
}
</style>
