<template>
	<view class="mine-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="avatar-wrapper">
					<image class="avatar" :src="userDetail.image || defaultImage"></image>
				</view>
				<view v-if="user" class="user-info" @click="getUser">
					<view class="user-name">登录/注册</view>
					<view class="user-desc">立即登录，开启健身之旅</view>
				</view>
				<view v-else class="user-info" @click="updateUser">
					<view class="user-name">{{userDetail.name}}</view>
					<view class="user-desc">{{userDetail.type}}</view>
				</view>
			</view>
		</view>
		
		<view class="content-section">
			<view class="stats-card" v-if="userDetail.type=='会员'">
				<view class="stat-item" v-for="item in navArr" @click="turn(item)">
					<view class="stat-icon">
						<image :src="item.icon"></image>
					</view>
					<view class="stat-info">
						<view class="stat-count">{{item.count}}</view>
						<view class="stat-label">{{item.text}}</view>
					</view>
				</view>
			</view>
			
			<view class="stats-card" v-else>
				<view class="stat-item" v-for="item in navArr1">
					<view class="stat-icon">
						<image :src="item.icon"></image>
					</view>
					<view class="stat-info">
						<view class="stat-count">{{item.count}}</view>
						<view class="stat-label">{{item.text}}</view>
					</view>
				</view>
			</view>
			
			<view class="service-card">
				<view class="card-header">
					<view class="card-title">我的服务</view>
					<view class="card-line"></view>
				</view>
				<view class="service-list">
					<view class="service-item" v-for="item in serviceArr" @click="turn(item)">
						<view class="service-icon-wrapper">
							<image :src="item.image"></image>
						</view>
						<view class="service-item-text">{{item.text}}</view>
						<view class="service-arrow">›</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getSelectedCardByUserId,
		getSelectedClassesByUserId,
		getUserById,
		getTeacherCount
	} from '../../api/user'
	import {
		baseUrl
	} from '../../config'
	
	export default {
		data() {
			return {
				defaultImage: "/static/img/teacher1.png",
				navArr: [{
						count: 0,
						text: "已选卡包",
						icon: "/static/img/card.png",
						pageUrl: "/pages/cardSelected/cardSelected"
					},
					{
						count: 0,
						text: "已选课程",
						icon: "/static/img/activity.png",
						pageUrl: "/pages/classesSelected/classesSelected"
					}
				],
				navArr1: [{
						count: 0,
						text: "总课时",
						icon: "/static/img/practice.png",
						pageUrl: "/pages/cardSelected/cardSelected"
					},
					{
						count: 0,
						text: "本月课时",
						icon: "/static/img/activity.png",
						pageUrl: "/pages/classesSelected/classesSelected"
					}
				],
				serviceArr: [{
						image: "/static/img/orders.png",
						text: "订单",
						pageUrl: "/pages/order/order"
					},
					{
						image: "/static/img/notice1.png",
						text: "通知",
						pageUrl: "/pages/notice/notice"
					}
				],
				user: false,
				userDetail: {}
			}
		},
		methods: {
			getUser() {
				uni.navigateTo({
					url: "/pages/login/login"
				})
			},
			updateUser() {
				uni.navigateTo({
					url: "/pages/detail/userDetail"
				})
			},
			turn(item) {
				uni.navigateTo({
					url: item.pageUrl
				})
			},
			init() {
				this.user = uni.getStorageSync("auth") == null
				var userId = uni.getStorageSync('auth')
				if (!this.user) {
					getUserById(userId).then(res => {
						res.data.data.image = baseUrl + "/common/download?name=" + res.data.data.image
						this.userDetail = res.data.data
						if(res.data.data.type=='会员'){
							getSelectedCardByUserId(uni.getStorageSync('auth')).then(res => {
								this.navArr[0].count = res.data.data.length
							})
							getSelectedClassesByUserId(uni.getStorageSync('auth')).then(res => {
								this.navArr[1].count = res.data.data.length
							})
						}else{
							getTeacherCount(userId).then(res=>{
								this.navArr1[0].count=res.data.data.allCount
								this.navArr1[1].count=res.data.data.mouthCount
							})
						}
					})
				}
			}
		},
		onLoad() {
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

.mine-container {
	min-height: 100vh;
}

.header-section {
	position: relative;
	margin-bottom: 24rpx;
}

.header-bg {
	height: 240rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 0 0 40rpx 40rpx;
}

.header-content {
	position: relative;
	display: flex;
	align-items: center;
	padding: 0 32rpx;
	margin-top: -120rpx;
}

.avatar-wrapper {
	width: 140rpx;
	height: 140rpx;
	border-radius: 50%;
	background: #fff;
	padding: 6rpx;
	box-shadow: 0 8rpx 32rpx rgba(59, 130, 246, 0.25);
}

.avatar {
	width: 100%;
	height: 100%;
	border-radius: 50%;
}

.user-info {
	flex: 1;
	margin-left: 24rpx;
	color: #fff;
}

.user-name {
	font-size: 36rpx;
	font-weight: bold;
	margin-bottom: 8rpx;
}

.user-desc {
	font-size: 26rpx;
	opacity: 0.9;
}

.content-section {
	padding: 0 24rpx 30rpx;
}

.stats-card {
	background: #fff;
	border-radius: 20rpx;
	padding: 32rpx 24rpx;
	margin-bottom: 24rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.stat-item {
	display: flex;
	align-items: center;
	padding: 24rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.stat-item:last-child {
	border-bottom: none;
}

.stat-icon {
	width: 80rpx;
	height: 80rpx;
	background: linear-gradient(135deg, #EFF6FF 0%, #DBEAFE 100%);
	border-radius: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 24rpx;
}

.stat-icon image {
	width: 48rpx;
	height: 48rpx;
}

.stat-info {
	flex: 1;
}

.stat-count {
	font-size: 48rpx;
	font-weight: bold;
	color: #1E293B;
	margin-bottom: 8rpx;
}

.stat-label {
	font-size: 26rpx;
	color: #64748B;
}

.service-card {
	background: #fff;
	border-radius: 20rpx;
	padding: 32rpx 24rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.card-header {
	display: flex;
	align-items: center;
	margin-bottom: 24rpx;
}

.card-title {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
}

.card-line {
	flex: 1;
	height: 4rpx;
	background: linear-gradient(90deg, #3B82F6 0%, #10B981 100%);
	margin-left: 16rpx;
	border-radius: 2rpx;
}

.service-list {
	display: flex;
	flex-direction: column;
}

.service-item {
	display: flex;
	align-items: center;
	padding: 24rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
	transition: background-color 0.2s;
}

.service-item:last-child {
	border-bottom: none;
}

.service-item:active {
	background-color: #F8FAFC;
}

.service-icon-wrapper {
	width: 80rpx;
	height: 80rpx;
	background: linear-gradient(135deg, #EFF6FF 0%, #DBEAFE 100%);
	border-radius: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 24rpx;
}

.service-icon-wrapper image {
	width: 48rpx;
	height: 48rpx;
}

.service-item-text {
	flex: 1;
	font-size: 30rpx;
	color: #1E293B;
	font-weight: 500;
}

.service-arrow {
	font-size: 40rpx;
	color: #CBD5E1;
}
</style>
