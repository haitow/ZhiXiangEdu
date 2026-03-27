<template>
	<view class="classes-selected-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">已选课程</view>
				<view class="header-subtitle">查看已预约的课程</view>
			</view>
		</view>
		
		<view class="tabs-section">
			<uni-segmented-control :current="current" :values="items" :style-type="styleType"
				:active-color="activeColor" @clickItem="onClickItem" />
		</view>
		
		<view class="class-list">
			<view class="class-card" v-if="current === 0 && leagueClass.length !== 0" v-for="item in leagueClass" :key="item.classId" @click="turn(item)">
				<view class="class-image">
					<image src="/static/img/practice.png" mode="aspectFill"></image>
				</view>
				<view class="class-info">
					<view class="class-name">{{item.className}}</view>
					<view class="class-tags">
						<text class="tag" v-for="(item1, index) in item.tags" :key="index">{{item1}}</text>
					</view>
					<view class="class-desc" v-if="item.description != null">{{item.description}}</view>
				</view>
				<view class="class-arrow">›</view>
			</view>
			
			<view class="empty-state" v-if="leagueClass.length === 0">
				<image src="/static/img/practice.png" class="empty-icon"></image>
				<text class="empty-text">暂无已选课程</text>
			</view>
		</view>
	</view>
</template>

<script>
	import { getClassList } from '../../api/class'
	import { chooseClasses, getSelectedClassesByUserId } from '../../api/user'
	import { baseUrl } from '../../config.js'
	
	export default {
		data() {
			return {
				current: 0,
				styleType: "text",
				activeColor: "#3B82F6",
				items: ["已选择"],
				leagueClass: []
			}
		},
		methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
				}
			},
			turn(item) {
				uni.navigateTo({
					url: "/pages/detail/classesDetail?id=" + item.classId
				})
			}
		},
		onLoad() {
			getSelectedClassesByUserId(uni.getStorageSync('auth')).then(res => {
				for (var i = 0; i < res.data.data.length; i++) {
					var tags = res.data.data[i].tags.split(",")
					res.data.data[i].tags = tags
				}
				this.leagueClass = res.data.data
			})
		}
	}
</script>

<style lang="scss">
page {
	background-color: #F8FAFC;
}

.classes-selected-container {
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

.class-list {
	padding: 0 24rpx 30rpx;
}

.class-card {
	display: flex;
	align-items: center;
	background: #fff;
	border-radius: 20rpx;
	padding: 24rpx;
	margin-bottom: 20rpx;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.class-image {
	width: 140rpx;
	height: 100rpx;
	border-radius: 12rpx;
	overflow: hidden;
	flex-shrink: 0;
}

.class-image image {
	width: 100%;
	height: 100%;
}

.class-info {
	flex: 1;
	margin-left: 20rpx;
	overflow: hidden;
}

.class-name {
	font-size: 30rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 12rpx;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.class-tags {
	display: flex;
	flex-wrap: wrap;
	margin-bottom: 8rpx;
}

.tag {
	display: inline-block;
	padding: 6rpx 16rpx;
	margin-right: 12rpx;
	font-size: 22rpx;
	color: #3B82F6;
	background: rgba(59, 130, 246, 0.1);
	border-radius: 20rpx;
}

.class-desc {
	font-size: 24rpx;
	color: #64748B;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.class-arrow {
	font-size: 40rpx;
	color: #CBD5E1;
	margin-left: 16rpx;
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
