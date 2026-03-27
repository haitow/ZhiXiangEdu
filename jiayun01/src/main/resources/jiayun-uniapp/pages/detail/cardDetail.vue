<template>
	<view class="card-detail-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="back-btn" @click="back">
					<text class="back-icon">‹</text>
				</view>
				<text class="header-title">卡片详情</text>
			</view>
		</view>
		
		<view class="main-content">
			<view class="card-preview">
				<image class="card-image" :src="cardDetail.image" mode="aspectFill"></image>
				<view class="card-overlay">
					<view class="card-badge">{{cardDetail.type}}</view>
					<text class="card-name">{{cardDetail.name}}</text>
					<view class="card-price-row">
						<text class="price-symbol">¥</text>
						<text class="price-value">{{cardDetail.price}}</text>
					</view>
				</view>
			</view>
			
			<view class="info-section">
				<view class="section-header">
					<view class="section-title">
						<text class="title-text">卡片信息</text>
						<view class="title-line"></view>
					</view>
				</view>
				<view class="info-list">
					<view class="info-item">
						<text class="info-label">有效天数</text>
						<text class="info-value">{{cardDetail.day}} 天</text>
					</view>
					<view class="info-item">
						<text class="info-label">卡片状态</text>
						<view class="status-tag" :class="{'active': cardDetail.status == '在售'}">
							{{cardDetail.status}}
						</view>
					</view>
					<view class="info-item full-width">
						<text class="info-label">卡片描述</text>
						<text class="info-desc">{{cardDetail.description || '暂无描述'}}</text>
					</view>
				</view>
			</view>
			
			<view class="course-section" v-if="classList.length > 0">
				<view class="section-header">
					<view class="section-title">
						<text class="title-text">支持课程</text>
						<view class="title-line"></view>
					</view>
					<text class="course-count">共{{classList.length}}门课程</text>
				</view>
				<view class="course-list">
					<view class="course-item" v-for="(item, index) in classList" :key="index">
						<view class="course-index">{{index + 1}}</view>
						<text class="course-name">{{item}}</text>
					</view>
				</view>
			</view>
		</view>
		
		<view class="bottom-bar" v-if="status==''">
			<view class="consult-btn" @click="showAdmin">
				<image src="/static/img/telephone.png" class="btn-icon"></image>
				<text>咨询</text>
			</view>
			<view class="buy-btn" @click="buttonClick">
				<text>立即购买</text>
			</view>
		</view>
		
		<uni-popup ref="popup" background-color="transparent">
			<view class="popup-container">
				<view class="popup-header">
					<text class="popup-title">选择支付方式</text>
					<view class="popup-close" @click="$refs.popup.close()">×</view>
				</view>
				<view class="popup-content">
					<view class="pay-option" @click="pageToPay">
						<image src="/static/img/wx.png" class="pay-icon"></image>
						<text class="pay-name">微信支付</text>
						<text class="pay-arrow">›</text>
					</view>
					<view class="pay-option" @click="showAdmin">
						<image src="/static/img/telephone.png" class="pay-icon"></image>
						<text class="pay-name">联系管理员</text>
						<text class="pay-arrow">›</text>
					</view>
				</view>
			</view>
		</uni-popup>
		
		<uni-popup ref="adminList" background-color="transparent">
			<view class="popup-container">
				<view class="popup-header">
					<text class="popup-title">联系管理员</text>
					<view class="popup-close" @click="$refs.adminList.close()">×</view>
				</view>
				<view class="popup-content">
					<view class="admin-item" v-for="item in adminList" :key="item.id" @click="call(item.phone)">
						<view class="admin-avatar">
							<text>{{item.name.charAt(0)}}</text>
						</view>
						<view class="admin-info">
							<text class="admin-name">{{item.name}}</text>
							<text class="admin-phone">{{item.phone}}</text>
						</view>
						<view class="call-btn">
							<text>拨打</text>
						</view>
					</view>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import { getAdminList } from '../../api/admin';
	import { getCardById } from '../../api/card';
	import { getClassByCardTags } from '../../api/class';
	import { getSelectedCard } from '../../api/user';
	import { baseUrl } from '../../config';

	export default {
		data() {
			return {
				cardDetail: {},
				classList: [],
				status: "",
				adminList: []
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			buttonClick() {
				var userId = uni.getStorageSync("auth")
				var cardId = this.cardDetail.id
				getSelectedCard(userId, cardId).then(res => {
					if (res.data.code == 1) {
						this.$refs.popup.open('bottom')
					} else {
						uni.showToast({
							title: res.data.msg,
							icon: "error"
						})
					}
				})
			},
			pageToPay() {
				this.$refs.popup.close()
				uni.navigateTo({
					url: "/pages/pay/pay"
				})
			},
			showAdmin() {
				this.$refs.popup.close()
				getAdminList().then(res => {
					this.adminList = res.data.data
				})
				this.$refs.adminList.open('bottom')
			},
			call(phone) {
				uni.makePhoneCall({
					phoneNumber: phone
				})
			}
		},
		onLoad(options) {
			this.status = options.status
			getCardById(options.id).then(res => {
				if (res.data.data != null) {
					this.cardDetail = res.data.data
					res.data.data.image = baseUrl + "/common/download?name=" + res.data.data.image
					if (res.data.data.type == "期限卡") {
						res.data.data.type = "期"
					} else if (res.data.data.type == "储值卡") {
						res.data.data.type = "储"
					} else if (res.data.data.type == "次卡") {
						res.data.data.type = "次"
					}
					if (res.data.data.status == 1) {
						res.data.data.status = "在售"
					} else if (res.data.data.status == 0) {
						res.data.data.status = "停售"
					}
					if (res.data.data.tags != null) {
						getClassByCardTags(res.data.data.tags).then(res => {
							this.classList = res.data.data
						})
					}
				}
			})
		}
	}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.card-detail-container {
	min-height: 100vh;
	padding-bottom: 120rpx;
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
	padding: 0 24rpx;
}

.card-preview {
	position: relative;
	border-radius: 24rpx;
	overflow: hidden;
	box-shadow: 0 8rpx 32rpx rgba(59, 130, 246, 0.2);
	margin-bottom: 24rpx;
}

.card-image {
	width: 100%;
	height: 360rpx;
}

.card-overlay {
	position: absolute;
	bottom: 0;
	left: 0;
	right: 0;
	padding: 32rpx;
	background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
}

.card-badge {
	display: inline-block;
	padding: 8rpx 20rpx;
	background: linear-gradient(135deg, #F97316 0%, #FB923C 100%);
	border-radius: 8rpx;
	font-size: 24rpx;
	color: #fff;
	font-weight: 600;
	margin-bottom: 16rpx;
}

.card-name {
	display: block;
	font-size: 36rpx;
	font-weight: bold;
	color: #fff;
	margin-bottom: 12rpx;
}

.card-price-row {
	display: flex;
	align-items: baseline;
}

.price-symbol {
	font-size: 28rpx;
	color: #F97316;
	font-weight: 600;
}

.price-value {
	font-size: 48rpx;
	font-weight: bold;
	color: #F97316;
}

.info-section, .course-section {
	background: #fff;
	border-radius: 20rpx;
	padding: 30rpx;
	margin-bottom: 24rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.section-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 24rpx;
}

.section-title {
	display: flex;
	align-items: center;
}

.title-text {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
}

.title-line {
	width: 6rpx;
	height: 28rpx;
	background: linear-gradient(180deg, #3B82F6 0%, #10B981 100%);
	border-radius: 3rpx;
	margin-left: 16rpx;
}

.course-count {
	font-size: 26rpx;
	color: #64748B;
}

.info-list {
	display: flex;
	flex-wrap: wrap;
}

.info-item {
	width: 50%;
	display: flex;
	flex-direction: column;
	padding: 20rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.info-item.full-width {
	width: 100%;
}

.info-label {
	font-size: 26rpx;
	color: #64748B;
	margin-bottom: 8rpx;
}

.info-value {
	font-size: 30rpx;
	color: #1E293B;
	font-weight: 500;
}

.status-tag {
	display: inline-block;
	padding: 6rpx 16rpx;
	border-radius: 6rpx;
	font-size: 24rpx;
	background: #FEE2E2;
	color: #EF4444;
}

.status-tag.active {
	background: #DCFCE7;
	color: #10B981;
}

.info-desc {
	font-size: 28rpx;
	color: #1E293B;
	line-height: 1.6;
}

.course-list {
	display: flex;
	flex-direction: column;
}

.course-item {
	display: flex;
	align-items: center;
	padding: 20rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.course-item:last-child {
	border-bottom: none;
}

.course-index {
	width: 48rpx;
	height: 48rpx;
	background: linear-gradient(135deg, #EFF6FF 0%, #DBEAFE 100%);
	border-radius: 12rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 24rpx;
	color: #3B82F6;
	font-weight: 600;
	margin-right: 20rpx;
}

.course-name {
	font-size: 28rpx;
	color: #1E293B;
}

.bottom-bar {
	position: fixed;
	bottom: 0;
	left: 0;
	right: 0;
	display: flex;
	align-items: center;
	padding: 20rpx 24rpx;
	padding-bottom: calc(20rpx + env(safe-area-inset-bottom));
	background: #fff;
	box-shadow: 0 -4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.consult-btn {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 16rpx 32rpx;
	margin-right: 20rpx;
}

.btn-icon {
	width: 40rpx;
	height: 40rpx;
	margin-bottom: 4rpx;
}

.consult-btn text {
	font-size: 24rpx;
	color: #64748B;
}

.buy-btn {
	flex: 1;
	height: 88rpx;
	background: linear-gradient(135deg, #F97316 0%, #FB923C 100%);
	border-radius: 16rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: 0 8rpx 24rpx rgba(249, 115, 22, 0.35);
}

.buy-btn text {
	font-size: 32rpx;
	font-weight: 600;
	color: #fff;
}

.popup-container {
	background: #fff;
	border-radius: 32rpx 32rpx 0 0;
	overflow: hidden;
}

.popup-header {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 32rpx;
	border-bottom: 1rpx solid #F1F5F9;
}

.popup-title {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
}

.popup-close {
	font-size: 48rpx;
	color: #94A3B8;
	line-height: 1;
}

.popup-content {
	padding: 24rpx 32rpx;
}

.pay-option {
	display: flex;
	align-items: center;
	padding: 24rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.pay-option:last-child {
	border-bottom: none;
}

.pay-icon {
	width: 48rpx;
	height: 48rpx;
	margin-right: 20rpx;
}

.pay-name {
	flex: 1;
	font-size: 30rpx;
	color: #1E293B;
}

.pay-arrow {
	font-size: 36rpx;
	color: #CBD5E1;
}

.admin-item {
	display: flex;
	align-items: center;
	padding: 24rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.admin-item:last-child {
	border-bottom: none;
}

.admin-avatar {
	width: 80rpx;
	height: 80rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 20rpx;
}

.admin-avatar text {
	font-size: 32rpx;
	font-weight: 600;
	color: #fff;
}

.admin-info {
	flex: 1;
}

.admin-name {
	display: block;
	font-size: 30rpx;
	color: #1E293B;
	font-weight: 500;
	margin-bottom: 4rpx;
}

.admin-phone {
	font-size: 26rpx;
	color: #64748B;
}

.call-btn {
	padding: 12rpx 24rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 8rpx;
}

.call-btn text {
	font-size: 26rpx;
	color: #fff;
}
</style>
