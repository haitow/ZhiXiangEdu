<template>
	<view class="class-detail-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="back-btn" @click="back">
					<text class="back-icon">‹</text>
				</view>
				<text class="header-title">课程详情</text>
			</view>
		</view>
		
		<view class="main-content">
			<view class="class-image-wrapper">
				<image class="class-image" :src="classDetail.imgs" mode="aspectFill"></image>
				<view class="image-overlay">
					<view class="class-name">{{classDetail.name}}</view>
					<view class="class-tags">{{classDetail.tags}}</view>
				</view>
			</view>
			
			<view class="info-section">
				<view class="section-header">
					<view class="section-title">
						<text class="title-text">课程信息</text>
						<view class="title-line"></view>
					</view>
				</view>
				<view class="info-list">
					<view class="info-item">
						<view class="info-icon teacher">
							<text>师</text>
						</view>
						<view class="info-content">
							<text class="info-label">代课老师</text>
							<text class="info-value">{{classDetail.tname}}</text>
						</view>
					</view>
					<view class="info-item">
						<view class="info-icon room">
							<text>室</text>
						</view>
						<view class="info-content">
							<text class="info-label">上课教室</text>
							<text class="info-value">{{classDetail.roomName}}</text>
						</view>
					</view>
					<view class="info-item">
						<view class="info-icon time">
							<text>时</text>
						</view>
						<view class="info-content">
							<text class="info-label">上课时间</text>
							<text class="info-value">{{classDetail.startTime}}</text>
						</view>
					</view>
					<view class="info-item">
						<view class="info-icon time">
							<text>结</text>
						</view>
						<view class="info-content">
							<text class="info-label">结束时间</text>
							<text class="info-value">{{classDetail.endTime}}</text>
						</view>
					</view>
				</view>
			</view>
			
			<view class="stats-section">
				<view class="stats-card">
					<view class="stats-item">
						<text class="stats-value">{{classDetail.pnum}}</text>
						<text class="stats-label">已选人数</text>
					</view>
					<view class="stats-divider"></view>
					<view class="stats-item">
						<text class="stats-value">{{classDetail.pmax}}</text>
						<text class="stats-label">上限人数</text>
					</view>
					<view class="stats-divider"></view>
					<view class="stats-item">
						<text class="stats-value highlight">{{classDetail.pmax - classDetail.pnum}}</text>
						<text class="stats-label">剩余名额</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
import { getClassByClassId } from '../../api/class';
import { baseUrl } from '../../config';

export default {
	data() {
		return {
			classDetail: {}
		}
	},
	methods: {
		back() {
			uni.navigateBack({
				delta: 1
			})
		}
	},
	onLoad(options) {
		getClassByClassId(options.id).then(res => {
			res.data.data.imgs = baseUrl + "/common/download?name=" + res.data.data.imgs
			this.classDetail = res.data.data
		})
	}
}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.class-detail-container {
	min-height: 100vh;
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
	padding: 0 24rpx 30rpx;
}

.class-image-wrapper {
	position: relative;
	border-radius: 24rpx;
	overflow: hidden;
	box-shadow: 0 8rpx 32rpx rgba(59, 130, 246, 0.2);
	margin-bottom: 24rpx;
}

.class-image {
	width: 100%;
	height: 360rpx;
}

.image-overlay {
	position: absolute;
	bottom: 0;
	left: 0;
	right: 0;
	padding: 32rpx;
	background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
}

.class-name {
	font-size: 36rpx;
	font-weight: bold;
	color: #fff;
	margin-bottom: 12rpx;
}

.class-tags {
	display: inline-block;
	padding: 8rpx 20rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-radius: 8rpx;
	font-size: 24rpx;
	color: #fff;
}

.info-section {
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

.info-list {
	display: flex;
	flex-direction: column;
}

.info-item {
	display: flex;
	align-items: center;
	padding: 20rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.info-item:last-child {
	border-bottom: none;
}

.info-icon {
	width: 64rpx;
	height: 64rpx;
	border-radius: 16rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 20rpx;
}

.info-icon text {
	font-size: 28rpx;
	font-weight: 600;
	color: #fff;
}

.info-icon.teacher {
	background: linear-gradient(135deg, #3B82F6 0%, #60A5FA 100%);
}

.info-icon.room {
	background: linear-gradient(135deg, #10B981 0%, #34D399 100%);
}

.info-icon.time {
	background: linear-gradient(135deg, #F97316 0%, #FB923C 100%);
}

.info-content {
	flex: 1;
	display: flex;
	flex-direction: column;
}

.info-label {
	font-size: 26rpx;
	color: #64748B;
	margin-bottom: 4rpx;
}

.info-value {
	font-size: 30rpx;
	color: #1E293B;
	font-weight: 500;
}

.stats-section {
	padding: 0;
}

.stats-card {
	background: #fff;
	border-radius: 20rpx;
	padding: 32rpx;
	display: flex;
	align-items: center;
	justify-content: space-around;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.stats-item {
	display: flex;
	flex-direction: column;
	align-items: center;
}

.stats-value {
	font-size: 48rpx;
	font-weight: bold;
	color: #1E293B;
	margin-bottom: 8rpx;
}

.stats-value.highlight {
	color: #F97316;
}

.stats-label {
	font-size: 26rpx;
	color: #64748B;
}

.stats-divider {
	width: 2rpx;
	height: 60rpx;
	background: #F1F5F9;
}
</style>
