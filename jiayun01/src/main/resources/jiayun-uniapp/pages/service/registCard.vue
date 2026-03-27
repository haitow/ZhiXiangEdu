<template>
	<view class="card-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">会员办卡</view>
				<view class="header-subtitle">选择适合你的会员卡</view>
			</view>
		</view>
		
		<view class="tabs-section">
			<scroll-view scroll-x class="tabs-scroll">
				<view class="tabs-wrapper">
					<view 
						class="tab-item" 
						:class="{'active': current === index}" 
						v-for="(item, index) in items" 
						:key="index"
						@click="onClickItem({currentIndex: index})"
					>
						{{item}}
					</view>
				</view>
			</scroll-view>
		</view>
		
		<view class="card-list">
			<view class="card-item" v-for="item in cardAll" :key="item.id" @click="cardDetail(item)">
				<view class="card-image">
					<image :src="item.image" mode="aspectFill"></image>
					<view class="card-badge">{{item.type}}</view>
				</view>
				<view class="card-info">
					<view class="card-name">{{item.name}}</view>
					<view class="card-desc" v-if="item.description">{{item.description}}</view>
					<view class="card-footer">
						<view class="card-price">
							<text class="price-label">¥</text>
							<text class="price-value">{{item.price}}</text>
						</view>
						<view class="card-btn">立即办理</view>
					</view>
				</view>
			</view>
			
			<view class="empty-state" v-if="cardAll.length === 0">
				<image src="/static/img/card.png" class="empty-icon"></image>
				<text class="empty-text">暂无会员卡</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		baseUrl
	} from '@/config.js'
	import {
		getCardList,
		getCardListByType
	} from '@/api/card.js'
	
	export default {
		data() {
			return {
				current: 0,
				items: ["全部", "期限卡", "次卡"],
				cardAll: []
			}
		},
		methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
				}
				
				if (this.current == 0) {
					this.init()
				} else {
					getCardListByType(this.items[this.current]).then(res => {
						for (var i = 0; i < res.data.data.length; i++) {
							res.data.data[i].image = baseUrl + "/common/download?name=" + res.data.data[i].image
						}
						this.cardAll = res.data.data
					})
				}
			},
			cardDetail(item) {
				uni.navigateTo({
					url: "/pages/detail/cardDetail?id=" + item.id
				})
			},
			init() {
				getCardList().then(res => {
					for (var i = 0; i < res.data.data.length; i++) {
						res.data.data[i].image = baseUrl + "/common/download?name=" + res.data.data[i].image
					}
					this.cardAll = res.data.data
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

.card-container {
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
	padding: 20rpx 0;
	margin-bottom: 20rpx;
}

.tabs-scroll {
	white-space: nowrap;
}

.tabs-wrapper {
	display: inline-flex;
	padding: 0 24rpx;
}

.tab-item {
	display: inline-block;
	padding: 16rpx 40rpx;
	margin-right: 20rpx;
	font-size: 28rpx;
	color: #64748B;
	background: #F1F5F9;
	border-radius: 30rpx;
	transition: all 0.3s;
}

.tab-item.active {
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
}

.card-list {
	padding: 0 24rpx 30rpx;
}

.card-item {
	background: #fff;
	border-radius: 20rpx;
	margin-bottom: 24rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.card-image {
	position: relative;
	width: 100%;
	height: 320rpx;
}

.card-image image {
	width: 100%;
	height: 100%;
}

.card-badge {
	position: absolute;
	top: 20rpx;
	right: 20rpx;
	padding: 8rpx 20rpx;
	background: linear-gradient(135deg, #ff6b6b 0%, #ff8e53 100%);
	color: #fff;
	font-size: 24rpx;
	border-radius: 20rpx;
}

.card-info {
	padding: 24rpx;
}

.card-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 12rpx;
}

.card-desc {
	font-size: 26rpx;
	color: #64748B;
	margin-bottom: 20rpx;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}

.card-footer {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding-top: 16rpx;
	border-top: 1rpx solid #F1F5F9;
}

.card-price {
	color: #F97316;
}

.price-label {
	font-size: 24rpx;
}

.price-value {
	font-size: 40rpx;
	font-weight: bold;
}

.card-btn {
	padding: 16rpx 40rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
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
	color: #999;
}
</style>
