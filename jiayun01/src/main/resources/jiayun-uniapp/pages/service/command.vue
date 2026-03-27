<template>
	<view class="command-container">
		<view class="header-section">
			<view class="header-bg"></view>
			<view class="header-content">
				<view class="header-title">名师推荐</view>
				<view class="header-subtitle">专业教练，助力成长</view>
			</view>
		</view>
		
		<view class="teacher-list">
			<view class="teacher-card" v-for="item in teacherList" :key="item.id">
				<view class="teacher-avatar">
					<image :src="item.image" mode="aspectFill"></image>
				</view>
				<view class="teacher-info">
					<view class="teacher-name">{{item.name}}</view>
					<view class="teacher-tags">
						<text class="tag" v-if="item.sex">{{item.sex}}</text>
						<text class="tag" v-if="item.specialty">{{item.specialty}}</text>
					</view>
					<view class="teacher-desc" v-if="item.description">{{item.description}}</view>
				</view>
				<view class="teacher-arrow">›</view>
			</view>
			
			<view class="empty-state" v-if="teacherList.length === 0">
				<image src="/static/img/teacher1.png" class="empty-icon"></image>
				<text class="empty-text">暂无老师</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getTeacherList
	} from '../../api/user'
	
	export default {
		data() {
			return {
				teacherList: []
			}
		},
		methods: {
			init() {
				getTeacherList().then(res => {
					console.log(res);
					if (res.data.code == 1 && res.data.data) {
						const teacherNames = res.data.data
						const teachers = []
						for (var i = 0; i < teacherNames.length; i++) {
							teachers.push({
								id: i,
								name: teacherNames[i],
								image: '/static/img/teacher1.png',
								sex: '',
								specialty: '',
								description: ''
							})
						}
						this.teacherList = teachers
					}
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

.command-container {
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

.teacher-list {
	padding: 0 24rpx 30rpx;
}

.teacher-card {
	display: flex;
	align-items: center;
	background: #fff;
	border-radius: 20rpx;
	padding: 24rpx;
	margin-bottom: 20rpx;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
}

.teacher-avatar {
	width: 120rpx;
	height: 120rpx;
	border-radius: 50%;
	overflow: hidden;
	flex-shrink: 0;
	border: 4rpx solid #EFF6FF;
}

.teacher-avatar image {
	width: 100%;
	height: 100%;
}

.teacher-info {
	flex: 1;
	margin-left: 24rpx;
	overflow: hidden;
}

.teacher-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 12rpx;
}

.teacher-tags {
	display: flex;
	flex-wrap: wrap;
	margin-bottom: 12rpx;
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

.teacher-desc {
	font-size: 26rpx;
	color: #64748B;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}

.teacher-arrow {
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
