<template>
	<view class="container">
		<view class="header">
			<view class="header-content">
				<view class="header-title">潮汐健身中心</view>
				<view class="header-subtitle">Tidal Fitness</view>
			</view>
		</view>
		
		<view class="main-content">
			<view class="banner">
				<swiper class="swiper-box" :interval="3000" :autoplay="true" circular indicator-dots indicator-color="rgba(255,255,255,0.5)" indicator-active-color="#fff">
					<swiper-item v-for="(item, index) in info" :key="index">
						<image class="banner-img" :src="item" mode="aspectFill"></image>
					</swiper-item>
				</swiper>
			</view>
			
			<view class="service-section">
				<view class="service-grid">
					<view class="service-item" v-for="(item, index) in services" :key="index" @click="serviceNav(item)">
						<view class="service-icon">
							<image :src="item.url"></image>
						</view>
						<text class="service-text">{{item.text}}</text>
					</view>
				</view>
			</view>
			
			<view class="section-card teacher-section">
				<view class="section-header">
					<view class="section-title">
						<text class="title-text">名师推荐</text>
						<text class="title-line"></text>
					</view>
					<view class="section-more" @click="commandMore">
						<text>更多</text>
						<text class="more-arrow">›</text>
					</view>
				</view>
				<view class="teacher-list">
					<view class="teacher-item" v-for="(item, index) in teachers" :key="index">
						<image class="teacher-avatar" :src="item.image"></image>
						<text class="teacher-name">{{item.name}}</text>
					</view>
				</view>
			</view>
			
			<view class="notice-section" v-if="noticeTitle.length > 0">
				<view class="notice-icon">
					<image src="/static/img/notice.png"></image>
				</view>
				<swiper class="notice-swiper" :interval="3000" :autoplay="true" circular vertical>
					<swiper-item v-for="(item, index) in noticeTitle" :key="index">
						<view class="notice-item">
							<text class="notice-title">{{item.title}}</text>
							<text class="notice-date">{{item.createTime}}</text>
						</view>
					</swiper-item>
				</swiper>
			</view>
			
			<view class="section-card contact-section">
				<view class="section-header">
					<view class="section-title">
						<text class="title-text">联系我们</text>
						<text class="title-line"></text>
					</view>
				</view>
				<view class="contact-list">
					<view class="contact-item">
						<view class="contact-left">
							<image class="contact-icon" src="/static/img/map.png"></image>
							<text class="contact-label">地址</text>
						</view>
						<text class="contact-value">{{bussinessInfo.address}}</text>
					</view>
					<view class="contact-item">
						<view class="contact-left">
							<image class="contact-icon" src="/static/img/telephone.png"></image>
							<text class="contact-label">电话</text>
						</view>
						<text class="contact-value">{{bussinessInfo.phone}}</text>
					</view>
					<view class="contact-item" @click="open">
						<view class="contact-left">
							<image class="contact-icon" src="/static/img/wx.png"></image>
							<text class="contact-label">微信</text>
						</view>
						<text class="contact-value">{{bussinessInfo.wx}}</text>
					</view>
				</view>
			</view>
		</view>
		
		<uni-popup ref="wx" type="center">
			<view class="wx-popup">
				<view class="wx-title">潮汐健身中心</view>
				<image class="wx-qrcode" src="/static/img/wxerweima.jpg"></image>
				<view class="wx-tip">微信扫码联系客服</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
import { getNoticeList } from '../../api/notice';
import { getTeacherListPage } from '../../api/teacher';
import { baseUrl } from '../../config';

export default {
	data() {
		return {
			bussinessInfo:{
				address:"银河系太阳系地球亚洲中国山东省",
				phone:"13333333333",
				wx:"潮汐健身"
			},
			info: ['/static/img/1.png','/static/img/2.png','/static/img/3.png'],
			services:[
				{
					url:"/static/img/card.png",
					text:"办卡",
					src:"/registCard"
				},
				{
					url:"/static/img/mall.png",
					text:"商城",
					src:"/mall"
				},
				{
					url:"/static/img/brand.png",
					text:"品牌",
					src:"/brand"
				},
				{
					url:"/static/img/activity.png",
					text:"活动",
					src:"/activity"
				},
			],
			teachers:[],
			noticeTitle:[]
		}
	},
	methods: {
		serviceNav(item) {
			uni.navigateTo({
				url:"/pages/service" + item.src
			})
		},
		commandMore(){
			uni.navigateTo({
				url:'/pages/service/command'
			})
		},
		open(){
			this.$refs.wx.open()
		}
	},
	onLoad(){
		getTeacherListPage(1,4).then(res=>{		
			for (var i = 0; i < res.data.data.records.length; i++) {
				res.data.data.records[i].image=baseUrl+"/common/download?name="+res.data.data.records[i].image
			}
			this.teachers=res.data.data.records
		})
		getNoticeList().then(res=>{
			this.noticeTitle=res.data.data
		})
	},
	onPullDownRefresh() {
		this.onLoad()
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

.container {
	min-height: 100vh;
}

.header {
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	padding: 0 30rpx 40rpx;
	padding-top: calc(var(--status-bar-height) + 40rpx);
}

.header-content {
	text-align: center;
	padding: 30rpx 0;
}

.header-title {
	font-size: 40rpx;
	font-weight: bold;
	color: #fff;
	letter-spacing: 4rpx;
	margin-bottom: 8rpx;
}

.header-subtitle {
	font-size: 24rpx;
	color: rgba(255, 255, 255, 0.9);
	letter-spacing: 2rpx;
}

.main-content {
	padding: 0 24rpx 30rpx;
	margin-top: -20rpx;
}

.banner {
	border-radius: 20rpx;
	overflow: hidden;
	box-shadow: 0 8rpx 32rpx rgba(59, 130, 246, 0.15);
}

.swiper-box {
	height: 300rpx;
}

.banner-img {
	width: 100%;
	height: 100%;
}

.service-section {
	background: #fff;
	border-radius: 20rpx;
	margin-top: 24rpx;
	padding: 30rpx 20rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.service-grid {
	display: flex;
	justify-content: space-around;
}

.service-item {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 16rpx 24rpx;
	transition: transform 0.2s;
}

.service-item:active {
	transform: scale(0.95);
}

.service-icon {
	width: 88rpx;
	height: 88rpx;
	background: linear-gradient(135deg, #EFF6FF 0%, #DBEAFE 100%);
	border-radius: 24rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 12rpx;
}

.service-icon image {
	width: 48rpx;
	height: 48rpx;
}

.service-text {
	font-size: 26rpx;
	color: #1E293B;
	font-weight: 500;
}

.section-card {
	background: #fff;
	border-radius: 20rpx;
	margin-top: 24rpx;
	padding: 30rpx;
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

.section-more {
	display: flex;
	align-items: center;
	font-size: 26rpx;
	color: #3B82F6;
	font-weight: 500;
}

.more-arrow {
	font-size: 32rpx;
	margin-left: 4rpx;
}

.teacher-list {
	display: flex;
	justify-content: space-around;
	flex-wrap: wrap;
}

.teacher-item {
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 25%;
	padding: 16rpx 0;
}

.teacher-avatar {
	width: 120rpx;
	height: 120rpx;
	border-radius: 50%;
	margin-bottom: 16rpx;
	border: 4rpx solid #EFF6FF;
}

.teacher-name {
	font-size: 26rpx;
	color: #1E293B;
	font-weight: 500;
}

.notice-section {
	display: flex;
	align-items: center;
	background: #fff;
	border-radius: 20rpx;
	margin-top: 24rpx;
	padding: 24rpx 30rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.06);
}

.notice-icon {
	width: 40rpx;
	height: 40rpx;
	margin-right: 20rpx;
}

.notice-icon image {
	width: 100%;
	height: 100%;
}

.notice-swiper {
	flex: 1;
	height: 48rpx;
}

.notice-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	height: 48rpx;
}

.notice-title {
	font-size: 28rpx;
	color: #1E293B;
	flex: 1;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.notice-date {
	font-size: 24rpx;
	color: #64748B;
	margin-left: 20rpx;
}

.contact-list {
	padding: 0 10rpx;
}

.contact-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 24rpx 0;
	border-bottom: 1rpx solid #F1F5F9;
}

.contact-item:last-child {
	border-bottom: none;
}

.contact-left {
	display: flex;
	align-items: center;
}

.contact-icon {
	width: 36rpx;
	height: 36rpx;
	margin-right: 16rpx;
}

.contact-label {
	font-size: 28rpx;
	color: #64748B;
	width: 60rpx;
}

.contact-value {
	font-size: 28rpx;
	color: #1E293B;
	flex: 1;
	text-align: right;
}

.wx-popup {
	background: #fff;
	border-radius: 24rpx;
	padding: 48rpx 60rpx;
	text-align: center;
}

.wx-title {
	font-size: 32rpx;
	font-weight: 600;
	color: #1E293B;
	margin-bottom: 32rpx;
}

.wx-qrcode {
	width: 300rpx;
	height: 300rpx;
	border-radius: 16rpx;
}

.wx-tip {
	font-size: 26rpx;
	color: #64748B;
	margin-top: 24rpx;
}
</style>
