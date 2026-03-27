<template>
	<view class="order-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">我的订单</view>
				<view class="header-subtitle">查看全部订单记录</view>
			</view>
		</view>
		
		<view class="search-section">
			<view class="search-box">
				<uni-icons type="search" size="18" color="#999"></uni-icons>
				<input 
					confirm-type="search" 
					class="search-input" 
					type="text" 
					placeholder="搜索商品名称" 
					@confirm="searchOrder"
					v-model="searchInput" 
				/>
				<view class="search-btn" @click="searchOrder">搜索</view>
			</view>
		</view>
		
		<view class="order-list">
			<view class="order-card" v-for="item in ordersList" :key="item.id">
				<view class="order-header">
					<view class="order-status" :class="{'status-pending': item.status == 1, 'status-completed': item.status == 2}">
						{{item.status == 1 ? '待付款' : '已完成'}}
					</view>
				</view>
				
				<view class="order-content" @click="turn(item.id)">
					<view class="order-image">
						<image :src="item.image" mode="aspectFill"></image>
					</view>
					<view class="order-info">
						<view class="order-name">{{item.shopName}}</view>
						<view class="order-desc" v-if="item.shopDescription">{{item.shopDescription}}</view>
						<view class="order-count">共{{item.shopCount}}件商品</view>
					</view>
					<view class="order-price">
						<view class="price-label">¥{{item.price}}</view>
						<view class="price-count" v-if="item.shopCount > 1">x{{item.shopCount}}</view>
					</view>
				</view>
				
				<view class="order-footer">
					<view class="order-total">
						实付款：<text class="total-price">¥{{item.account}}</text>
					</view>
					<view class="order-actions">
						<view class="action-btn delete-btn" @click="deleteOrder(item.id)">删除订单</view>
						<view class="action-btn pay-btn" v-if="item.status == 1">立即付款</view>
						<view class="action-btn comment-btn" v-if="item.status == 2" @click="openPopup(item.shopId)">发表评论</view>
					</view>
				</view>
			</view>
			
			<view class="empty-state" v-if="ordersList.length === 0">
				<image src="/static/img/goods.png" class="empty-icon"></image>
				<text class="empty-text">暂无订单</text>
			</view>
		</view>
		
		<uni-popup ref="inputDialog" type="dialog">
			<view class="comment-popup">
				<view class="popup-header">
					<text class="popup-title">发表评论</text>
					<text class="popup-close" @click="$refs.inputDialog.close()">×</text>
				</view>
				<view class="popup-content">
					<view class="form-item">
						<text class="form-label">评论内容</text>
						<textarea 
							class="form-textarea" 
							v-model="baseFormData.content" 
							placeholder="请输入评论内容"
							maxlength="200"
						></textarea>
					</view>
					<view class="form-item">
						<text class="form-label">商品评分</text>
						<view class="rate-wrapper">
							<uni-rate v-model="baseFormData.storage" :size="20" activeColor="#4facfe"></uni-rate>
							<text class="rate-text">{{baseFormData.storage || 0}}分</text>
						</view>
					</view>
				</view>
				<view class="popup-footer">
					<view class="cancel-btn" @click="$refs.inputDialog.close()">取消</view>
					<view class="confirm-btn" @click="dialogInputConfirm">提交评论</view>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import {
		submitComment
	} from '../../api/comment'
	import {
		deleteOrder1,
		getOrdersList
	} from '../../api/orders'
	import {
		baseUrl
	} from '../../config'
	
	export default {
		data() {
			return {
				searchInput: "",
				ordersList: [],
				baseFormData: {
					content: '',
					storage: 5
				},
				currentId: ""
			}
		},
		methods: {
			searchOrder() {
				var _this = this
				var param = {
					userId: uni.getStorageSync('auth'),
					shopName: this.searchInput
				}
				if (!param.shopName) {
					delete param.shopName
				}
				getOrdersList(param).then(res => {
					for (var i = 0; i < res.data.data.length; i++) {
						res.data.data[i].image = baseUrl + "/common/download?name=" + res.data.data[i].image
					}
					_this.ordersList = res.data.data
				})
			},
			turn(id) {
				
			},
			deleteOrder(id) {
				var _this = this
				uni.showModal({
					title: "提示",
					content: "确定要删除此订单吗？",
					success(r) {
						if (r.confirm) {
							deleteOrder1(id).then(res => {
								uni.showToast({
									title: "删除成功",
									icon: "success"
								})
								_this.searchOrder()
							})
						}
					}
				})
			},
			openPopup(e) {
				this.currentId = e
				this.baseFormData = {
					content: '',
					storage: 5
				}
				this.$refs.inputDialog.open()
			},
			dialogInputConfirm() {
				if (!this.baseFormData.content) {
					uni.showToast({
						title: "请输入评论内容",
						icon: "none"
					})
					return
				}
				var param = {
					shopId: this.currentId,
					userId: uni.getStorageSync('auth'),
					...this.baseFormData
				}
				submitComment(param).then(res => {
					uni.showToast({
						title: "评论成功",
						icon: "success"
					})
					this.$refs.inputDialog.close()
					this.baseFormData = {
						content: '',
						storage: 5
					}
				})
			}
		},
		onLoad() {
			this.searchOrder()
		},
		onPullDownRefresh() {
			this.searchOrder()
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

.order-container {
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

.search-section {
	padding: 0 24rpx 24rpx;
}

.search-box {
	display: flex;
	align-items: center;
	background: #fff;
	border-radius: 40rpx;
	padding: 16rpx 24rpx;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.search-input {
	flex: 1;
	margin: 0 16rpx;
	font-size: 28rpx;
}

.search-btn {
	padding: 12rpx 32rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
	font-size: 26rpx;
	border-radius: 30rpx;
}

.order-list {
	padding: 0 24rpx 30rpx;
}

.order-card {
	background: #fff;
	border-radius: 20rpx;
	margin-bottom: 20rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.order-header {
	display: flex;
	justify-content: flex-end;
	padding: 20rpx 24rpx;
	border-bottom: 1rpx solid #F1F5F9;
}

.order-status {
	font-size: 26rpx;
	font-weight: 500;
}

.status-pending {
	color: #F97316;
}

.status-completed {
	color: #10B981;
}

.order-content {
	display: flex;
	padding: 24rpx;
}

.order-image {
	width: 160rpx;
	height: 160rpx;
	border-radius: 12rpx;
	overflow: hidden;
	flex-shrink: 0;
}

.order-image image {
	width: 100%;
	height: 100%;
}

.order-info {
	flex: 1;
	margin-left: 20rpx;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.order-name {
	font-size: 30rpx;
	font-weight: 600;
	color: #1E293B;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
}

.order-desc {
	font-size: 24rpx;
	color: #64748B;
	margin-top: 8rpx;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
}

.order-count {
	font-size: 24rpx;
	color: #64748B;
	margin-top: 8rpx;
}

.order-price {
	display: flex;
	flex-direction: column;
	align-items: flex-end;
	justify-content: space-between;
}

.price-label {
	font-size: 30rpx;
	font-weight: 600;
	color: #1E293B;
}

.price-count {
	font-size: 24rpx;
	color: #64748B;
}

.order-footer {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 20rpx 24rpx;
	border-top: 1rpx solid #F1F5F9;
}

.order-total {
	font-size: 26rpx;
	color: #64748B;
}

.total-price {
	font-size: 32rpx;
	font-weight: bold;
	color: #F97316;
}

.order-actions {
	display: flex;
}

.action-btn {
	padding: 12rpx 28rpx;
	font-size: 24rpx;
	border-radius: 30rpx;
	margin-left: 16rpx;
}

.delete-btn {
	border: 1rpx solid #E2E8F0;
	color: #64748B;
}

.pay-btn {
	background: linear-gradient(135deg, #F97316 0%, #FB923C 100%);
	color: #fff;
}

.comment-btn {
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
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

.comment-popup {
	width: 600rpx;
	background: #fff;
	border-radius: 20rpx;
	overflow: hidden;
}

.popup-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 30rpx;
	border-bottom: 1rpx solid #F1F5F9;
}

.popup-title {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
}

.popup-close {
	font-size: 40rpx;
	color: #94A3B8;
}

.popup-content {
	padding: 30rpx;
}

.form-item {
	margin-bottom: 30rpx;
}

.form-label {
	display: block;
	font-size: 28rpx;
	color: #1E293B;
	margin-bottom: 16rpx;
}

.form-textarea {
	width: 100%;
	height: 200rpx;
	padding: 20rpx;
	font-size: 28rpx;
	border: 1rpx solid #E2E8F0;
	border-radius: 12rpx;
	box-sizing: border-box;
}

.rate-wrapper {
	display: flex;
	align-items: center;
}

.rate-text {
	margin-left: 20rpx;
	font-size: 28rpx;
	color: #3B82F6;
}

.popup-footer {
	display: flex;
	border-top: 1rpx solid #F1F5F9;
}

.cancel-btn,
.confirm-btn {
	flex: 1;
	padding: 30rpx;
	text-align: center;
	font-size: 30rpx;
}

.cancel-btn {
	color: #64748B;
	border-right: 1rpx solid #F1F5F9;
}

.confirm-btn {
	color: #3B82F6;
	font-weight: 500;
}
</style>
