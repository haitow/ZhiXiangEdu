<template>
	<view class="pay-container">
		<view class="pay-content">
			<view class="success-icon">
				<text>✓</text>
			</view>
			<text class="pay-title">支付成功</text>
			<text class="pay-subtitle">恭喜您，订单已支付完成</text>
			<view class="countdown">
				<text class="countdown-text">{{count}}秒后自动返回</text>
			</view>
			<view class="action-btns">
				<view class="btn-primary" @click="goBack">
					<text>立即返回</text>
				</view>
				<view class="btn-secondary" @click="goHome">
					<text>返回首页</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			count: 3
		}
	},
	methods: {
		goBack() {
			uni.navigateBack({
				delta: 1
			})
		},
		goHome() {
			uni.switchTab({
				url: '/pages/index/index'
			})
		}
	},
	onLoad() {
		var _this = this
		var time = setInterval(r => {
			_this.count = _this.count - 1
			if (_this.count == 0) {
				clearInterval(time)
				uni.showLoading({
					title: ''
				})
				setTimeout(function() {
					uni.hideLoading()
					uni.navigateBack({
						delta: 1
					})
				}, 500)
			}
		}, 1000)
	}
}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.pay-container {
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 48rpx;
}

.pay-content {
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 100%;
}

.success-icon {
	width: 160rpx;
	height: 160rpx;
	background: linear-gradient(135deg, #10B981 0%, #34D399 100%);
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 40rpx;
	box-shadow: 0 8rpx 32rpx rgba(16, 185, 129, 0.3);
}

.success-icon text {
	font-size: 80rpx;
	color: #fff;
	font-weight: bold;
}

.pay-title {
	font-size: 40rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 16rpx;
}

.pay-subtitle {
	font-size: 28rpx;
	color: #64748B;
	margin-bottom: 48rpx;
}

.countdown {
	padding: 20rpx 40rpx;
	background: #EFF6FF;
	border-radius: 40rpx;
	margin-bottom: 48rpx;
}

.countdown-text {
	font-size: 28rpx;
	color: #3B82F6;
}

.action-btns {
	display: flex;
	flex-direction: column;
	width: 100%;
	gap: 24rpx;
}

.btn-primary {
	width: 100%;
	height: 96rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 16rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: 0 8rpx 24rpx rgba(59, 130, 246, 0.3);
}

.btn-primary text {
	font-size: 32rpx;
	font-weight: 600;
	color: #fff;
}

.btn-secondary {
	width: 100%;
	height: 96rpx;
	background: #fff;
	border-radius: 16rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	border: 2rpx solid #E2E8F0;
}

.btn-secondary text {
	font-size: 32rpx;
	font-weight: 500;
	color: #64748B;
}
</style>
