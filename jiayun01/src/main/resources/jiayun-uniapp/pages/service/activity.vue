<template>
	<view class="activity-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">限时活动</view>
				<view class="header-subtitle">超值优惠，不容错过</view>
			</view>
		</view>
		
		<view class="activity-list">
			<view class="activity-card" v-for="item in activityList" :key="item.id">
				<view class="activity-image">
					<image :src="item.image" mode="aspectFill"></image>
					<view class="activity-badge" v-if="item.type">{{item.type}}</view>
				</view>
				<view class="activity-info">
					<view class="activity-name">{{item.activityName}}</view>
					<view class="activity-desc" v-if="item.description">{{item.description}}</view>
					<view class="activity-footer">
						<view class="price-wrapper">
							<view class="current-price">
								<text class="price-label">¥</text>
								<text class="price-value">{{item.nowPrice}}</text>
							</view>
							<view class="original-price" v-if="item.oldPrice">
								<text>¥{{item.oldPrice}}</text>
							</view>
						</view>
						<view class="buy-btn">立即抢购</view>
					</view>
				</view>
			</view>
			
			<view class="empty-state" v-if="activityList.length === 0">
				<image src="/static/img/activity.png" class="empty-icon"></image>
				<text class="empty-text">暂无活动</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getActivityList
	} from '../../api/activity'
	import {
		baseUrl
	} from '../../config.js'
	
	export default {
		data() {
			return {
				activityList: []
			}
		},
		methods: {
			init() {
				getActivityList().then(res => {
					for (var i = 0; i < res.data.data.length; i++) {
						res.data.data[i].image = baseUrl + '/common/download?name=' + res.data.data[i].image
					}
					this.activityList = res.data.data
				})
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

.activity-container {
	min-height: 100vh;
}

.header-section {
	position: relative;
	margin-bottom: 24rpx;
}

.header-bg {
	height: 200rpx;
	background: linear-gradient(135deg, #F97316 0%, #FB923C 100%);
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

.activity-list {
	padding: 0 24rpx 30rpx;
}

.activity-card {
	background: #fff;
	border-radius: 20rpx;
	margin-bottom: 24rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.activity-image {
	position: relative;
	width: 100%;
	height: 320rpx;
}

.activity-image image {
	width: 100%;
	height: 100%;
}

.activity-badge {
	position: absolute;
	top: 20rpx;
	left: 20rpx;
	padding: 8rpx 20rpx;
	background: linear-gradient(135deg, #F97316 0%, #FB923C 100%);
	color: #fff;
	font-size: 24rpx;
	border-radius: 20rpx;
}

.activity-info {
	padding: 24rpx;
}

.activity-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 12rpx;
}

.activity-desc {
	font-size: 26rpx;
	color: #64748B;
	margin-bottom: 20rpx;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}

.activity-footer {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding-top: 16rpx;
	border-top: 1rpx solid #F1F5F9;
}

.price-wrapper {
	display: flex;
	align-items: baseline;
}

.current-price {
	color: #F97316;
	margin-right: 16rpx;
}

.price-label {
	font-size: 24rpx;
}

.price-value {
	font-size: 40rpx;
	font-weight: bold;
}

.original-price {
	color: #94A3B8;
	font-size: 26rpx;
	text-decoration: line-through;
}

.buy-btn {
	padding: 16rpx 40rpx;
	background: linear-gradient(135deg, #F97316 0%, #FB923C 100%);
	color: #fff;
	font-size: 28rpx;
	border-radius: 30rpx;
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
	color: #64748B;
}
</style>
