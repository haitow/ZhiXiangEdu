<template>
	<view class="container">
		<view class="title">
			珈云健身中心
		</view>
		<view class="course" v-for="item in course"v-if="course.length!==0" >
				
					<view class="course-type1" v-if="item.type=='团课'">
						{{item.type}}
					</view>
					<view class="course-type2" v-else>
						{{item.type}}
					</view>
				
					<image :src="item.imgSrc"></image>
					<view class="info">
						<text class="course-courseName">{{item.courseName}}</text>
						
						<view class="course-tags" >
							<text v-for="(item1,index) in item.tag">
								{{item1}}
							</text>
						</view> 
						<view class="course-price">
							{{item.price}}
						</view>
							
						
						<view class="course-try">
								体验
						</view>
						
					</view>
					
				
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				course:[
					{
						imgSrc:"/static/img/practice.png",
						courseName:"飞天瑜伽1114",
						tag:["五小时前","不可取消"],
						price:"免费",
						type:"团课"
						
					},
					{
						imgSrc:"/static/img/practice.png",
						courseName:"飞天瑜伽1114",
						tag:["五小时前","不可取消"],
						price:"免费",
						type:"团课"
						
					},
					{
						imgSrc:"/static/img/practice.png",
						courseName:"印度本地瑜伽",
						tag:["学习精品"],
						price:"免费",
						type:"团课"
						
					},{
						imgSrc:"/static/img/practice.png",
						courseName:"芭蕾",
						tag:[],
						price:"免费",
						type:"私教"
					},
					{
						imgSrc:"/static/img/practice.png",
						courseName:"芭蕾",
						tag:[],
						price:"免费",
						type:"私教"
					},
					{
						imgSrc:"/static/img/practice.png",
						courseName:"芭蕾",
						tag:[],
						price:"免费",
						type:"私教"
					},
					{
						imgSrc:"/static/img/practice.png",
						courseName:"芭蕾",
						tag:[],
						price:"免费",
						type:"私教"
					}
					
					
				]
			}
		},
		methods: {
			
		}
	}
</script>
<style>
	*{
		margin: 0;
		padding: 0;
	}
	body{
		display: flex;
		width: 100%;
	}
	.container{
		width: 100%;
	}
	.title{
		font-size: 3vh;
		font-weight: bold;
		margin: 2vh;
	}
	.course{
		display: flex;
		width: 90%;
		background-color: white;
		margin: 3vh auto;
		border-radius: 2vh;
		height: 15vh;
		position: relative;
	}
	.course image{
		margin: 2vh;
		width: 18vh;
		height: 10vh;
		display: flex;
		justify-content: left;
		border-radius: 1vh;

	}
	.info{
		display: flex;
		flex-direction: column;
		flex-wrap: wrap;
		margin-top: 2vh;
		width: 18vh;
		height: 10vh;
		display: flex;
		justify-content: left;
		
	}
	.course-courseName{
		font-weight: bold;
	}
	.course-tags{
		margin:7 0vh;
		position: absolute;
		top: 5vh;
	}
	.course-tags text{
		width: 5vh;
		display: inline-block;
		background-color: white;
		color: #B7B7B7;
		font-size: 1vh;
		border: 1px solid #B7B7B7;
		padding: 0.1vh;
		margin:  0.2vh;
		border-radius: 0.5vh;
		text-align: center;
		
		
	}
	.course-price{
		color: red;
		font-size: 1.5vh;
		margin-top: 1vh;
		font-weight: bold;
		position: absolute;
		top: 8vh;
	}
	.course-try{
		
		background-color: #15CCBB;
		color: white;
		width: 8vh;
		height: 4vh;
		text-align: center;
		border-radius: 10vh;
		line-height: 4vh;
		position: absolute;
		top: 8vh;
		right: 2vh;
	}
	.course-type1{
		width: 3vh;
		height: 2vh;
		position: absolute;
		background-color: #3AB4FC;
		color: #FFFFFF;
		top: 2vh;
		left: 2vh;
		border-radius: 1vh 0vh 1vh 0vh;
		font-size: 1vh;
		text-align: center;
		line-height: 2.2vh;
	}
	.course-type2{
		width: 3vh;
		height: 2vh;
		position: absolute;
		background-color: #FD8933;
		color: #FFFFFF;
		top: 2vh;
		left: 2vh;
		border-radius: 1vh 0vh 1vh 0vh;
		font-size: 1vh;
		text-align: center;
		line-height: 2.2vh;
	}
	
</style>