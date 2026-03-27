<template>
	<view>
		<view class="container">
			<view class="headlines">
				<view class="headlines-item" v-for="item in news">
					<view class="headlines-item-title">
						{{item.title}}
					</view>
					<view class="headline-item-description">
						{{item.description}}
					</view>
					<image :src="item.imgSrc"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				news:[
				
				]
			}
		},
		methods: {
			
		}
	}
</script>

<style>
	.container{
		display: flex;
		justify-content: center;
		width: 100%;
	}
	.headlines{
		width: 90%;
		
	}
	.headlines-item{
		display: flex;
		position: relative;
		background-color: #FFFFFF;
		border-radius: 2vh;
		height: 20vh;
		width: 100%;
		margin-top: 4vh;
		white-space: initial;
		overflow: hidden;
		text-overflow: ellipsis;
		
	}
	.headlines-item-title{
		color: indianred;
		display: flex;
		position: absolute;
		flex-wrap: wrap;
		width: 18vh;
		font-weight: bold;
		top: 2vh;
		left: 2vh;
		
	}
	.headline-item-description{
		display: flex;
		position: absolute;
		flex-wrap: nowrap;
		width: 19vh;
		height: 8vh;
		font-size: 1.5vh;
		
		top: 10vh;
		left: 2vh;
	}
	.headlines-item image{
		position: absolute;
		width: 20vh;
		height: 13vh;
		top: 3.5vh;
		right: 3vh;
		border-radius: 2vh;
	}
</style>
