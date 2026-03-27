<template>
	<view>
		<view class="content">
			<view class="album" v-for="item in albums" >
				<view class="album-item" >
					<image :src="'/static/img2/'+item.albumArr[0]"></image>
					<view class="album-item-count">
						<uni-icons type="image-filled" color="white"size="20"></uni-icons>
						<text class="album-item-count-text">
							{{item.albumArr.length}}张
						</text>
					</view>
					<text class="album-item-title">{{item.title}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				albums:[
					{
						albumArr:["image103.jpg","image102.jpg","image103.jpg","image104.jpg"],
						title:"推荐产品"
					},
					{
						albumArr:["image101.jpg"],
						title:"环境"
					},
					{
						albumArr:["image101.jpg","image102.jpg","image103.jpg","image104.jpg"],
						title:"教室环境"
					},
					
					
					
				]
			}
		},
		methods: {
			
		}
	}
</script>

<style>
	.content{
		display: flex;
		
		justify-content: center;
		flex-wrap: wrap;
	}
	.album{
		
	}
	.album-item{
		display: flex;
		margin: 3vh auto;
		width: 100%;
		position: relative;
		
	}
	.album-item image{
		border-radius: 3vh;
	
		
	}
	.album-item text{
		position: absolute;
	}
	
	.album-item-count{
		
		position: absolute;
		height: 3vh;
		background-color: #435963;
		border-radius: 5vh;
		width: 9vh;
		padding-left: 1.5vh;
		box-sizing: content-box;
		padding-top: 0.5vh;
		top: 10%;
		right: 3vh;
		z-index: 2;
		color: white;
	}
	.album-item-count-text{
		top: 10%;
		right: 1.8vh;
		font-size: 2vh;
		
	}
	.album-item-title{
		background-color: white;
		width: 14vh;
		text-align: center;
		border-radius: 3vh;
		color: black;
		font-weight: bold;
		top: 80%;
		left: 2vh;
	}
</style>
