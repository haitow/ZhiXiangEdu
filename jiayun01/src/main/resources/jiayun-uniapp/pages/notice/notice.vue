<template>
	<view class="notice-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">消息通知</view>
				<view class="header-subtitle">查看系统通知消息</view>
			</view>
		</view>
		
		<view class="notice-list">
			<view class="notice-card" v-for="item in noticeList" :key="item.id">
				<view class="notice-header">
					<view class="notice-icon">
						<image src="/static/img/notice.png"></image>
					</view>
					<view class="notice-title">{{item.title}}</view>
					<view class="notice-time">{{item.createTime}}</view>
				</view>
				<view class="notice-content">
					<view class="notice-text">{{item.description}}</view>
				</view>
			</view>
			
			<view class="empty-state" v-if="noticeList.length === 0">
				<image src="/static/img/notice.png" class="empty-icon"></image>
				<text class="empty-text">暂无通知消息</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getNoticeList
	} from '../../api/notice'
	
	export default {
		data() {
			return {
				noticeList: []
			}
		},
		methods: {
			init() {
				getNoticeList().then(res => {
					this.noticeList = res.data.data || []
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

.notice-container {
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

.notice-list {
	padding: 0 24rpx 30rpx;
}

.notice-card {
	background: #fff;
	border-radius: 20rpx;
	margin-bottom: 20rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.notice-header {
	display: flex;
	align-items: center;
	padding: 24rpx;
	border-bottom: 1rpx solid #F1F5F9;
}

.notice-icon {
	width: 48rpx;
	height: 48rpx;
	margin-right: 16rpx;
}

.notice-icon image {
	width: 100%;
	height: 100%;
}

.notice-title {
	flex: 1;
	font-size: 30rpx;
	font-weight: 600;
	color: #1E293B;
}

.notice-time {
	font-size: 24rpx;
	color: #64748B;
}

.notice-content {
	padding: 24rpx;
}

.notice-text {
	font-size: 28rpx;
	color: #64748B;
	line-height: 1.6;
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
