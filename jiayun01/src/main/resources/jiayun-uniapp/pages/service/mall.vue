<template>
	<view class="mall-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">潮汐商城</view>
				<view class="header-subtitle">精选好物，品质生活</view>
			</view>
		</view>
		
		<view class="quick-entry">
			<view class="entry-item" v-for="item in services" :key="item.text" @click="navTo(item.url)">
				<view class="entry-icon">
					<image :src="item.imgSrc"></image>
				</view>
				<text class="entry-text">{{item.text}}</text>
			</view>
		</view>
		
		<view class="mall-content">
			<view class="category-nav">
				<scroll-view scroll-y class="category-scroll">
					<view 
						class="category-item" 
						:class="{'active': currentCategory === item.id}" 
						v-for="item in categoryList" 
						:key="item.id"
						@click="initShop(item.id)"
					>
						<text>{{item.name}}</text>
					</view>
				</scroll-view>
			</view>
			
			<view class="product-list">
				<view class="product-item" v-for="item in shopList" :key="item.id" @click="shopDetail(item)">
					<view class="product-image">
						<image :src="item.image" mode="aspectFill"></image>
						<view class="product-status" v-if="item.status == 0">已停售</view>
					</view>
					<view class="product-info">
						<view class="product-name">{{item.shopName}}</view>
						<view class="product-desc">{{item.description}}</view>
						<view class="product-footer">
							<view class="price-box">
								<view class="current-price">
									<text class="price-label">¥</text>
									<text class="price-value">{{item.newPrice}}</text>
								</view>
								<view class="original-price" v-if="item.oldPrice">
									<text>¥{{item.oldPrice}}</text>
								</view>
							</view>
							<view class="buy-btn" v-if="item.status == 1">购买</view>
							<view class="disabled-btn" v-else>停售</view>
						</view>
					</view>
				</view>
				
				<view class="empty-state" v-if="shopList.length === 0">
					<image src="/static/img/goods.png" class="empty-icon"></image>
					<text class="empty-text">暂无商品</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getCategoryList
	} from '../../api/category'
	import {
		getShopByCategoryId,
	} from '../../api/shop'
	import {
		baseUrl
	} from '../../config'
	
	export default {
		data() {
			return {
				services: [{
					imgSrc: "/static/img/card.png",
					text: "办卡",
					url: "/pages/service/registCard"
				}, {
					imgSrc: "/static/img/practice.png",
					text: "课程包",
					url: "/pages/service/course"
				}, {
					imgSrc: "/static/img/goods.png",
					text: "商品",
					url: ""
				}, {
					imgSrc: "/static/img/activity.png",
					text: "积分兑换",
					url: ""
				}],
				categoryList: [],
				shopList: [],
				currentCategory: null
			}
		},
		methods: {
			navTo(url) {
				if (url) {
					uni.navigateTo({ url })
				}
			},
			initShop(categoryId) {
				this.currentCategory = categoryId
				getShopByCategoryId(categoryId).then(res => {
					this.shopList = []
					if (res.data.data && res.data.data.length > 0) {
						for (var i = 0; i < res.data.data.length; i++) {
							res.data.data[i].image = baseUrl + "/common/download?name=" + res.data.data[i].image
						}
						this.shopList = res.data.data
					}
				})
			},
			shopDetail(item) {
				if (item.status == 0) {
					uni.showToast({
						title: "商品已停售",
						icon: "none"
					})
				} else {
					uni.navigateTo({
						url: "/pages/detail/shopDetail?id=" + item.id
					})
				}
			}
		},
		onLoad() {
			getCategoryList().then(res => {
				this.categoryList = res.data.data
				if (res.data.data.length > 0) {
					this.initShop(res.data.data[0].id)
				}
			})
		},
		onPullDownRefresh() {
			if (this.currentCategory) {
				this.initShop(this.currentCategory)
			}
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

.mall-container {
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

.quick-entry {
	display: flex;
	justify-content: space-around;
	background: #fff;
	margin: 0 24rpx 24rpx;
	padding: 32rpx 16rpx;
	border-radius: 20rpx;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.entry-item {
	display: flex;
	flex-direction: column;
	align-items: center;
}

.entry-icon {
	width: 80rpx;
	height: 80rpx;
	background: linear-gradient(135deg, #EFF6FF 0%, #DBEAFE 100%);
	border-radius: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 12rpx;
}

.entry-icon image {
	width: 44rpx;
	height: 44rpx;
}

.entry-text {
	font-size: 26rpx;
	color: #1E293B;
}

.mall-content {
	display: flex;
	min-height: calc(100vh - 400rpx);
	margin: 0 24rpx;
}

.category-nav {
	width: 160rpx;
	background: #fff;
	border-radius: 20rpx 0 0 20rpx;
	overflow: hidden;
}

.category-scroll {
	height: 100%;
}

.category-item {
	padding: 28rpx 16rpx;
	text-align: center;
	font-size: 26rpx;
	color: #64748B;
	border-left: 4rpx solid transparent;
	transition: all 0.3s;
}

.category-item.active {
	background: #F8FAFC;
	color: #3B82F6;
	border-left-color: #3B82F6;
	font-weight: 500;
}

.product-list {
	flex: 1;
	background: #F8FAFC;
	border-radius: 0 20rpx 20rpx 0;
	padding: 20rpx 0 20rpx 20rpx;
}

.product-item {
	display: flex;
	background: #fff;
	border-radius: 16rpx;
	margin-bottom: 20rpx;
	padding: 20rpx;
	box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.04);
}

.product-image {
	position: relative;
	width: 180rpx;
	height: 180rpx;
	border-radius: 12rpx;
	overflow: hidden;
	flex-shrink: 0;
}

.product-image image {
	width: 100%;
	height: 100%;
}

.product-status {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: rgba(0, 0, 0, 0.5);
	display: flex;
	align-items: center;
	justify-content: center;
	color: #fff;
	font-size: 26rpx;
}

.product-info {
	flex: 1;
	margin-left: 20rpx;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.product-name {
	font-size: 30rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 8rpx;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
}

.product-desc {
	font-size: 24rpx;
	color: #64748B;
	margin-bottom: 16rpx;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}

.product-footer {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.price-box {
	display: flex;
	align-items: baseline;
}

.current-price {
	color: #F97316;
	margin-right: 12rpx;
}

.price-label {
	font-size: 22rpx;
}

.price-value {
	font-size: 34rpx;
	font-weight: bold;
}

.original-price {
	color: #94A3B8;
	font-size: 24rpx;
	text-decoration: line-through;
}

.buy-btn {
	padding: 12rpx 32rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
	font-size: 26rpx;
	border-radius: 30rpx;
}

.disabled-btn {
	padding: 12rpx 32rpx;
	background: #E2E8F0;
	color: #94A3B8;
	font-size: 26rpx;
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
	color: #999;
}
</style>
