<template>
	<view class="brand-container">
		<view class="header-section">
			<view class="header-bg">
				<image src="/static/img/1.png" mode="aspectFill"></image>
			</view>
			<view class="header-content">
				<view class="logo-wrapper">
					<image class="logo" src="/static/img/brand.png"></image>
				</view>
				<view class="brand-name">潮汐健身中心</view>
				<view class="brand-slogan">Tidal Fitness · 开启健康生活</view>
			</view>
		</view>
		
		<view class="content-section">
			<view class="intro-card">
				<view class="card-header">
					<view class="card-title">关于我们</view>
					<view class="card-line"></view>
				</view>
				<view class="intro-content">
					<view class="intro-text">
						潮汐健身中心是一家专注于提供高品质健身服务的现代化健身场所。我们拥有先进的健身设备、专业的教练团队和舒适的运动环境，致力于为每一位会员打造专属的健康生活方式。
					</view>
					<view class="intro-text">
						我们提供多样化的健身课程，包括瑜伽、普拉提、动感单车、搏击操、力量训练等，满足不同会员的健身需求。无论你是健身新手还是资深爱好者，都能在这里找到适合自己的训练方式。
					</view>
					<view class="intro-features">
						<view class="feature-item">
							<view class="feature-icon">
								<image src="/static/img/practice.png"></image>
							</view>
							<view class="feature-text">专业教练</view>
						</view>
						<view class="feature-item">
							<view class="feature-icon">
								<image src="/static/img/card.png"></image>
							</view>
							<view class="feature-text">多样课程</view>
						</view>
						<view class="feature-item">
							<view class="feature-icon">
								<image src="/static/img/activity.png"></image>
							</view>
							<view class="feature-text">优质环境</view>
						</view>
						<view class="feature-item">
							<view class="feature-icon">
								<image src="/static/img/service.png"></image>
							</view>
							<view class="feature-text">贴心服务</view>
						</view>
					</view>
				</view>
			</view>
			
			<view class="contact-card">
				<view class="card-header">
					<view class="card-title">联系我们</view>
					<view class="card-line"></view>
				</view>
				<view class="contact-list">
					<view class="contact-item">
						<view class="contact-left">
							<view class="contact-icon">
								<image src="/static/img/map.png"></image>
							</view>
							<view class="contact-label">地址</view>
						</view>
						<view class="contact-value">{{bussinessInfo.address}}</view>
					</view>
					<view class="contact-item">
						<view class="contact-left">
							<view class="contact-icon">
								<image src="/static/img/telephone.png"></image>
							</view>
							<view class="contact-label">电话</view>
						</view>
						<view class="contact-value">{{bussinessInfo.phone}}</view>
					</view>
					<view class="contact-item" @click="open">
						<view class="contact-left">
							<view class="contact-icon">
								<image src="/static/img/wx.png"></image>
							</view>
							<view class="contact-label">微信</view>
						</view>
						<view class="contact-value">{{bussinessInfo.wx}}</view>
						<view class="contact-arrow">›</view>
					</view>
				</view>
			</view>
		</view>
		
		<uni-popup ref="wx" type="center">
			<view class="wx-popup">
				<view class="popup-header">
					<text class="popup-title">潮汐健身中心</text>
					<text class="popup-close" @click="$refs.wx.close()">×</text>
				</view>
				<view class="popup-content">
					<image src="/static/img/wxerweima.jpg" class="qrcode"></image>
					<text class="popup-tip">微信扫码联系客服</text>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				bussinessInfo: {
					address: "银河系太阳系地球亚洲中国山东省",
					phone: "13333333333",
					wx: "潮汐健身"
				}
			}
		},
		methods: {
			open() {
				this.$refs.wx.open()
			}
		}
	}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.brand-container {
	min-height: 100vh;
}

.header-section {
	position: relative;
	height: 400rpx;
	margin-bottom: 24rpx;
}

.header-bg {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}

.header-bg image {
	width: 100%;
	height: 100%;
}

.header-bg::after {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: linear-gradient(135deg, rgba(59, 130, 246, 0.7) 0%, rgba(16, 185, 129, 0.7) 100%);
}

.header-content {
	position: relative;
	z-index: 1;
	display: flex;
	flex-direction: column;
	align-items: center;
	padding-top: 80rpx;
}

.logo-wrapper {
	width: 140rpx;
	height: 140rpx;
	border-radius: 50%;
	background: #fff;
	padding: 8rpx;
	box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.15);
	margin-bottom: 24rpx;
}

.logo {
	width: 100%;
	height: 100%;
	border-radius: 50%;
}

.brand-name {
	font-size: 40rpx;
	font-weight: bold;
	color: #fff;
	margin-bottom: 8rpx;
	text-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.2);
}

.brand-slogan {
	font-size: 26rpx;
	color: rgba(255, 255, 255, 0.9);
}

.content-section {
	padding: 0 24rpx 30rpx;
}

.intro-card,
.contact-card {
	background: #fff;
	border-radius: 20rpx;
	padding: 32rpx 24rpx;
	margin-bottom: 24rpx;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
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

.intro-content {
	
}

.intro-text {
	font-size: 28rpx;
	color: #64748B;
	line-height: 1.8;
	margin-bottom: 20rpx;
	text-align: justify;
}

.intro-features {
	display: flex;
	justify-content: space-around;
	margin-top: 32rpx;
	padding-top: 24rpx;
	border-top: 1rpx solid #F1F5F9;
}

.feature-item {
	display: flex;
	flex-direction: column;
	align-items: center;
}

.feature-icon {
	width: 80rpx;
	height: 80rpx;
	background: linear-gradient(135deg, #EFF6FF 0%, #DBEAFE 100%);
	border-radius: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 12rpx;
}

.feature-icon image {
	width: 44rpx;
	height: 44rpx;
}

.feature-text {
	font-size: 26rpx;
	color: #1E293B;
}

.contact-list {
	display: flex;
	flex-direction: column;
}

.contact-item {
	display: flex;
	align-items: center;
	padding: 24rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.contact-item:last-child {
	border-bottom: none;
}

.contact-left {
	display: flex;
	align-items: center;
	width: 160rpx;
}

.contact-icon {
	width: 40rpx;
	height: 40rpx;
	margin-right: 12rpx;
}

.contact-icon image {
	width: 100%;
	height: 100%;
}

.contact-label {
	font-size: 28rpx;
	color: #64748B;
}

.contact-value {
	flex: 1;
	font-size: 28rpx;
	color: #1E293B;
}

.contact-arrow {
	font-size: 36rpx;
	color: #CBD5E1;
}

.wx-popup {
	width: 560rpx;
	background: #fff;
	border-radius: 24rpx;
	overflow: hidden;
}

.popup-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 32rpx;
	border-bottom: 1rpx solid #F1F5F9;
}

.popup-title {
	font-size: 34rpx;
	font-weight: 600;
	color: #1E293B;
}

.popup-close {
	font-size: 48rpx;
	color: #94A3B8;
	line-height: 1;
}

.popup-content {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 40rpx;
}

.qrcode {
	width: 320rpx;
	height: 320rpx;
	margin-bottom: 24rpx;
}

.popup-tip {
	font-size: 28rpx;
	color: #64748B;
}
</style>
