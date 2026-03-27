<template>
	<view class="card-selected-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">我的卡片</view>
				<view class="header-subtitle">查看已购买的会员卡</view>
			</view>
		</view>
		
		<view class="tabs-section">
			<uni-segmented-control :current="current" :values="items" :style-type="styleType"
				:active-color="activeColor" @clickItem="onClickItem" />
		</view>
		
		<view class="card-list">
			<view class="card-item" v-if="current === 0 && cardAll.length !== 0" v-for="item in cardAll" :key="item.id" @click="cardDetail(item)">
				<image class="card-image" :src="item.image" mode="aspectFill"></image>
				<view class="card-overlay">
					<view class="card-badge">{{item.type}}</view>
					<text class="card-name">{{item.cardName}}</text>
					<view class="card-price-row">
						<text class="price-symbol">¥</text>
						<text class="price-value">{{item.price}}</text>
					</view>
				</view>
			</view>
			
			<view class="empty-state" v-if="cardAll.length === 0">
				<image src="/static/img/goods.png" class="empty-icon"></image>
				<text class="empty-text">暂无卡片</text>
			</view>
		</view>
	</view>
</template>

<script>
	import { baseUrl } from '@/config.js'
	import { getCardList } from '@/api/card.js'
	import { getSelectedCardByUserId } from '../../api/user'
	
	export default {
		data() {
			return {
				current: 0,
				styleType: "text",
				activeColor: "#3B82F6",
				items: ["可使用"],
				cardAll: [],
				stored: []
			}
		},
		methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
				}
			},
			change(res) {
				for (var i = 0; i < res.data.data.length; i++) {
					if (res.data.data[i].type == "期限卡") {
						res.data.data[i].type = "期"
					} else if (res.data.data[i].type == "储值卡") {
						res.data.data[i].type = "储"
					} else if (res.data.data[i].type == "次卡") {
						res.data.data[i].type = "次"
					}
				}
			},
			cardDetail(item) {
				uni.navigateTo({
					url: "/pages/detail/cardDetail?id=" + item.id + "&status=false"
				})
			}
		},
		onLoad() {
			var id = uni.getStorageSync('auth')
			getSelectedCardByUserId(id).then(res => {
				for (var i = 0; i < res.data.data.length; i++) {
					res.data.data[i].image = baseUrl + "/common/download?name=" + res.data.data[i].image
				}
				this.cardAll = res.data.data
				this.change(res)
			})
		}
	}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.card-selected-container {
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

.tabs-section {
	background: #fff;
	padding: 20rpx 24rpx;
	margin-bottom: 20rpx;
}

.card-list {
	padding: 0 24rpx 30rpx;
}

.card-item {
	position: relative;
	border-radius: 24rpx;
	overflow: hidden;
	margin-bottom: 24rpx;
	box-shadow: 0 8rpx 32rpx rgba(59, 130, 246, 0.15);
}

.card-image {
	width: 100%;
	height: 320rpx;
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
	color: #fff;
	font-weight: 600;
}

.price-value {
	font-size: 48rpx;
	font-weight: bold;
	color: #fff;
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
