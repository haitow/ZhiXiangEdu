<template>
	<view class="shop-detail-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="back-btn" @click="back">
					<text class="back-icon">‹</text>
				</view>
				<text class="header-title">商品详情</text>
			</view>
		</view>
		
		<view class="main-content">
			<view class="shop-image">
				<image :src="shopDetail.image" mode="aspectFill"></image>
			</view>
			
			<view class="shop-info">
				<view class="shop-name">{{shopDetail.shopName}}</view>
				<view class="price-row">
					<view class="price-wrapper">
						<text class="price-symbol">¥</text>
						<text class="price-value">{{shopDetail.newPrice}}</text>
					</view>
					<view class="inventory">库存: {{shopDetail.inventory}}</view>
				</view>
				<view class="count-row">
					<text class="count-label">购买数量</text>
					<view class="count-control">
						<view class="count-btn" @click="decreaseCount">-</view>
						<text class="count-value">{{count}}</text>
						<view class="count-btn" @click="increaseCount">+</view>
					</view>
				</view>
			</view>
			
			<view class="detail-section">
				<view class="section-header">
					<view class="section-title">
						<text class="title-text">商品详情</text>
						<view class="title-line"></view>
					</view>
				</view>
				<view class="detail-content">
					<text class="detail-label">商品描述</text>
					<text class="detail-text">{{shopDetail.description || '暂无描述'}}</text>
				</view>
			</view>
			
			<view class="comment-section">
				<view class="section-header">
					<view class="section-title">
						<text class="title-text">用户评价</text>
						<view class="title-line"></view>
					</view>
					<text class="comment-count">共{{commentList.length}}条</text>
				</view>
				<view class="comment-list">
					<view class="comment-item" v-for="item in commentList" :key="item.id">
						<view class="comment-avatar">
							<image :src="item.user.image" mode="aspectFill"></image>
						</view>
						<view class="comment-content">
							<view class="comment-header">
								<text class="comment-name">{{item.user.name}}</text>
								<text class="comment-time">{{item.createTime}}</text>
							</view>
							<view class="comment-rate">
								<uni-rate :readonly="true" :value="item.storage" :size="16" activeColor="#F97316" />
							</view>
							<text class="comment-text">{{item.content}}</text>
						</view>
					</view>
					
					<view class="empty-state" v-if="commentList.length === 0">
						<text class="empty-text">暂无评价</text>
					</view>
				</view>
			</view>
		</view>
		
		<view class="bottom-bar">
			<view class="total-price">
				<text class="total-label">合计:</text>
				<text class="total-symbol">¥</text>
				<text class="total-value">{{(shopDetail.newPrice * count).toFixed(2)}}</text>
			</view>
			<view class="buy-btn" @click="buttonClick">
				<text>立即购买</text>
			</view>
		</view>
	</view>
</template>

<script>
	import { getCommentsByShopId } from '../../api/comment';
	import { placeOrder } from '../../api/orders';
	import { pay } from '../../api/pay';
	import { getShopById } from '../../api/shop'
	import { baseUrl } from '../../config';
	
	export default {
		data() {
			return {
				shopDetail: {},
				commentList: [],
				count: 1
			}
		},
		methods: {
			back() {
				uni.navigateBack({
					delta: 1
				})
			},
			buttonClick() {
				var param = {
					"userId": uni.getStorageSync('auth'),
					"shopId": this.shopDetail.id,
					"shopCount": this.count
				}
				
				uni.showModal({
					content: "是否购买此商品?",
					success(r) {
						if (r.confirm) {
							placeOrder(param).then(res => {
								uni.navigateTo({
									url: "/pages/pay/pay"
								})
							})
						}
					}
				})
			},
			increaseCount() {
				if (this.count < this.shopDetail.inventory) {
					this.count = this.count + 1
				} else {
					uni.showToast({
						title: "库存数量不足",
						icon: "error"
					})
				}
			},
			decreaseCount() {
				if (this.count > 1) {
					this.count = this.count - 1
				}
			}
		},
		onLoad(options) {
			getShopById(options.id).then(res => {
				res.data.data.image = baseUrl + "/common/download?name=" + res.data.data.image
				this.shopDetail = res.data.data
				getCommentsByShopId(res.data.data.id).then(res => {
					for (var i = 0; i < res.data.data.length; i++) {
						res.data.data[i].user.image = baseUrl + "/common/download?name=" + res.data.data[i].user.image
					}
					this.commentList = res.data.data
				})
			})
		}
	}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.shop-detail-container {
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

.shop-image {
	width: 100%;
	height: 400rpx;
	border-radius: 20rpx;
	overflow: hidden;
	margin-bottom: 24rpx;
	box-shadow: 0 8rpx 32rpx rgba(0, 0, 0, 0.1);
}

.shop-image image {
	width: 100%;
	height: 100%;
}

.shop-info {
	background: #fff;
	border-radius: 20rpx;
	padding: 30rpx;
	margin-bottom: 24rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.shop-name {
	font-size: 36rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 20rpx;
}

.price-row {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 24rpx;
	padding-bottom: 20rpx;
	border-bottom: 1rpx solid #F1F5F9;
}

.price-wrapper {
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

.inventory {
	font-size: 26rpx;
	color: #64748B;
}

.count-row {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.count-label {
	font-size: 28rpx;
	color: #64748B;
}

.count-control {
	display: flex;
	align-items: center;
}

.count-btn {
	width: 56rpx;
	height: 56rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 12rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 36rpx;
	color: #fff;
}

.count-value {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
	margin: 0 32rpx;
	min-width: 48rpx;
	text-align: center;
}

.detail-section, .comment-section {
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

.comment-count {
	font-size: 26rpx;
	color: #64748B;
}

.detail-content {
	display: flex;
	flex-direction: column;
}

.detail-label {
	font-size: 26rpx;
	color: #64748B;
	margin-bottom: 12rpx;
}

.detail-text {
	font-size: 28rpx;
	color: #1E293B;
	line-height: 1.6;
}

.comment-list {
	display: flex;
	flex-direction: column;
}

.comment-item {
	display: flex;
	padding: 20rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.comment-item:last-child {
	border-bottom: none;
}

.comment-avatar {
	width: 80rpx;
	height: 80rpx;
	border-radius: 50%;
	overflow: hidden;
	flex-shrink: 0;
	margin-right: 20rpx;
}

.comment-avatar image {
	width: 100%;
	height: 100%;
}

.comment-content {
	flex: 1;
}

.comment-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 12rpx;
}

.comment-name {
	font-size: 28rpx;
	font-weight: 500;
	color: #1E293B;
}

.comment-time {
	font-size: 24rpx;
	color: #94A3B8;
}

.comment-rate {
	margin-bottom: 12rpx;
}

.comment-text {
	font-size: 26rpx;
	color: #64748B;
	line-height: 1.5;
}

.empty-state {
	display: flex;
	justify-content: center;
	padding: 40rpx 0;
}

.empty-text {
	font-size: 28rpx;
	color: #94A3B8;
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

.total-price {
	flex: 1;
	display: flex;
	align-items: baseline;
}

.total-label {
	font-size: 28rpx;
	color: #64748B;
	margin-right: 8rpx;
}

.total-symbol {
	font-size: 28rpx;
	color: #F97316;
	font-weight: 600;
}

.total-value {
	font-size: 40rpx;
	font-weight: bold;
	color: #F97316;
}

.buy-btn {
	padding: 24rpx 64rpx;
	background: linear-gradient(135deg, #F97316 0%, #FB923C 100%);
	border-radius: 16rpx;
	box-shadow: 0 8rpx 24rpx rgba(249, 115, 22, 0.35);
}

.buy-btn text {
	font-size: 32rpx;
	font-weight: 600;
	color: #fff;
}
</style>
