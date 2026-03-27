<template>
	<view class="content">
		<view class="nav" :style="{ 'height': Height + 'px' }">
			<view class="bar" :style="{ 'height': Height + 'px' }">
				<view class="itembar"
					:style="{ background: index === current ? '#f8f8f8' : '#ffffff',color:index===current ? '#00aaff':'black'}"
					v-for="(item,index) in barlist" :key="item.id" :data-current="index" @click="select">
					{{item.name}}
				</view>
			</view>
			<!-- 右侧内容循环遍历的方法 也可不用循环的方法  复制多个main 如下 ....-->
			<!--例如: <view class="main" :style="{ 'height': Height + 'px' }" v-show="current==0">内容</view> -->
			
			<!-- 循环方法 -->
			<view class="mains" v-for="(item,index) in barlist.length" :key="item.id">
				<!--根据current显示不同内容 -->
				<view class="main" :style="{ 'height': Height + 'px' }" v-show="current==index">
					<!-- 为了区分显示内容删除即可 -->
					这是内容{{index}} 为了区分显示内容删除即可
					<scroll-view scroll-y="true" show-scrollbar :style="{ 'height': Height + 'px' }" class="scroll-Y">
						<view id="demo1" class="scroll-view-item ">
							<view class="left">
								<image src="../../static/img_login.png" mode=""></image>
							</view>
							<view class="right">
								<view class="one">
									商品表题商品表题
								</view>
								<view class="two">
									<text>月售99</text>
								</view>
								<view class="three">
									<image src="../../static/me.png" mode=""></image>
									<text>8.2折</text>
									<text>限一份</text>
								</view>
								<view class="four">
									<view class="af">
										￥14.4
									</view>
									<view class="bf">
										￥29.8
									</view>
									<view class="cf">
										选规格
									</view>
								</view>
							</view>
						</view>
						<view id="demo2" class="scroll-view-item ">
							<view class="left">
								<image src="../../static/img_login.png" mode=""></image>
							</view>
							<view class="right">
								<view class="one">
									商品表题商品表题
								</view>
								<view class="two">
									<text>月售99</text>
								</view>
								<view class="three">
									<image src="../../static/me.png" mode=""></image>
									<text>8.2折</text>
									<text>限一份</text>
								</view>
								<view class="four">
									<view class="af">
										￥14.4
									</view>
									<view class="bf">
										￥29.8
									</view>
									<view class="cf">
										选规格
									</view>
								</view>
							</view>
						</view>
						<view id="demo3" class="scroll-view-item uni-bg-blue">
							<view class="left">
								<image src="../../static/img_login.png" mode=""></image>
							</view>
							<view class="right">
								<view class="one">
									商品表题商品表题
								</view>
								<view class="two">
									<text>月售99</text>
								</view>
								<view class="three">
									<image src="../../static/me.png" mode=""></image>
									<text>8.2折</text>
									<text>限一份</text>
								</view>
								<view class="four">
									<view class="af">
										￥14.4
									</view>
									<view class="bf">
										￥29.8
									</view>
									<view class="cf">
										选规格
									</view>
								</view>
							</view>
						</view>
						<view id="demo1" class="scroll-view-item uni-bg-red">
							<view class="left">
								<image src="../../static/img_login.png" mode=""></image>
							</view>
							<view class="right">
								<view class="one">
									商品表题商品表题
								</view>
								<view class="two">
									<text>月售99</text>
								</view>
								<view class="three">
									<image src="../../static/me.png" mode=""></image>
									<text>8.2折</text>
									<text>限一份</text>
								</view>
								<view class="four">
									<view class="af">
										￥14.4
									</view>
									<view class="bf">
										￥29.8
									</view>
									<view class="cf">
										选规格
									</view>
								</view>
							</view>
						</view>
						<view id="demo2" class="scroll-view-item uni-bg-green">E</view>
						<view id="demo3" class="scroll-view-item uni-bg-blue">F</view>
					</scroll-view>
				</view>
			</view>
			
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				current: 0,
				Height: '',
				barlist: [{
					name: '推荐',
					id: '0'
				}, {
					name: '折扣',
					id: '1'
				}, {
					name: '进店必买',
					id: '2'
				}, {
					name: '主食',
					id: '3'
				}, {
					name: '口水鸡',
					id: '4'
				}, {
					name: '饮料',
					id: '5'
				}, {
					name: '麻辣系列',
					id: '6'
				}, {
					name: '水煮系列',
					id: '7'
				}]
			}
		},
		mounted() {
			// 注意，这里要用个变量存this，不然进到getSystemInfo后this指向会变化，找不到data变量
			var _this = this
			uni.getSystemInfo({
				success: function(res) {
					console.log(res.screenHeight); // 屏幕高度，包含导航栏
					console.log(res.windowHeight); // 能够使用的窗口高度，不包含导航栏
					console.log(res.screenWidth); // 屏幕宽度
					console.log(res.windowWidth); //  能够使用的窗口宽度
					_this.Height = res.windowHeight;

				}
			});
		},
		methods: {
			select(e) {
				// 选中的分类下标
				let index = e.target.dataset.current || e.currentTarget.dataset.current;
				console.log(index);
				this.current = index;
			},

		}

	}
</script>

<style>
	/* 侧边导航栏部分 */
	.nav {
		width: 120upx;
		max-width: 150upx;
		display: flex;
		justify-content: space-between;

	}

	.nav>.bar {
		/* width: 20%; */
		width: 150upx;
		max-width: 150upx;
		/* width: 150upx; */
		display: flex;
		flex-direction: column;

	}

	.nav>.bar>.itembar {
		min-width: 150upx;
		text-align: center;
		padding: 30upx 0upx;
		font-size: 26upx;

	}

	.nav>.main {
		max-width: 800upx;
		min-width: 560upx;
		background-color: #f8f8f8;
	}

	scroll-view {
		/* 去除滚动条 */
		scrollbar-width: none !important;
		-ms-overflow-style: none !important;
	}

	.scroll-view-item {
		max-width: 800upx;
		min-width: 560upx;
		height: 300upx;
		/* 	margin: 20upx 20upx; */
		background-color: #ffffff;
		display: flex;
		align-items: center;
	}

	.scroll-view-item>.left>image {
		width: 200upx;
		height: 200upx;
		margin: 0 10upx;
		border-radius: 10upx;
	}

	.scroll-view-item>.right {
		max-width: 600upx;
		min-width: 360upx;
		height: 300upx;

	}

	.scroll-view-item>.right>.one {
		width: max-content;
		height: 50upx;
		margin-top: 30upx;
		font-size: 36upx;
		text-align: left;
	}

	.scroll-view-item>.right>.two {
		height: 40upx;
		font-size: 22upx;
		color: #cecece;
		text-align: left;
		margin: 50upx 0 10upx;
	}

	.scroll-view-item>.right>.three {
		height: 40upx;
		font-size: 22upx;
		line-height: 40upx;
		color: red;
	}

	.scroll-view-item>.right>.three>image {
		width: 20upx;
		height: 20upx;
	}

	.scroll-view-item>.right>.three>text {
		margin: 0 5upx;
		font-size: 22upx;
	}

	.scroll-view-item>.right>.four {
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.scroll-view-item>.right>.four>.af {
		font-size: 28upx;
		color: red;
	}

	.scroll-view-item>.right>.four>.bf {
		display: flex;
		justify-content: space-between;
		font-size: 22upx;
		color: #cecece;
		text-decoration: line-through;
	}

	.scroll-view-item>.right>.four>.cf {
		font-size: 24upx;
		background-color: #00aaff;
		padding: 5upx 10upx;
		border-radius: 10upx;
		display: flex;
		justify-content: space-between;
		margin-right: 20upx;
	}
</style>