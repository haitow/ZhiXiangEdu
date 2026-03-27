<template>
	<view>
		<view class="uni-padding-wrap uni-common-mt">
			<uni-segmented-control :current="current" :values="items" :style-type="styleType"
				:active-color="activeColor" @clickItem="onClickItem" />
		</view>
		<view class="content">
			<view v-if="current === 0">
				<!-- <view class="course-date">
					<view class="course-date-item" v-for="item in date" @click="change(item)">
						<view class="course-date-item-date" :class="item.class2" >
							{{item.date}}
						</view>
						<view class="course-date-item-text" :class="item.class1">
							{{item.text}}
						</view>
					</view>
					
				</view> -->
				<view class="course">
					<view class="course-item" v-for="item in classes">
						<view class="course-item-time">
							{{item.endTime}}
						</view>
						<view class="course-item-image">
							<image src="/static/img2/favicon.png"></image>
						</view>
						<view class="course-item-title">
							{{item.name}}
						</view>
						<view class="course-item-tname">
							{{item.tname}}
						</view>
					
						<view class="course-item-tags">
							<view class="course-item-tags-item" v-for="item1 in item.tags">
								{{item1}}
							</view>
						</view>
						<view class="course-item-price">
							¥{{item.price}}
						</view>
						<view class="ensure" v-if="item.pnum<item.pmax">
							预约
						</view>
						<view class="ensure-full" v-else>
							预约已满
						</view>
					</view>
				</view>
			</view>
			<view v-if="current === 1"><text class="content-text">选项卡2的内容</text></view>
			<view v-if="current === 2"><text class="content-text">选项卡3的内容</text></view>
		</view>
	</view>
</template>
<script>
	import dayjs from 'dayjs'
import { getClassList } from '../../api/class'
	export default {
		data() {
			return {
				items: ['团课', '私教', '精品课'],
				current: 0,
				activeColor: '#15CCBB',
				styleType: 'text',
				date: [{
						date: dayjs().day(),
						text: "今",
						class1: "selected",
						class2: "selectedText",
					},
					{
						date: dayjs().add(1 * 24 * 60 * 60 * 1000).day(),
						text: dayjs().add(1 * 24 * 60 * 60 * 1000).date(),
						class1: "disSelected",
						class2: "disSelectedText",
					}, {
						date: dayjs().add(2 * 24 * 60 * 60 * 1000).day(),
						text: dayjs().add(2 * 24 * 60 * 60 * 1000).date(),
						class1: "disSelected",
						class2: "disSelectedText",
					}, {
						date: dayjs().add(3 * 24 * 60 * 60 * 1000).day(),
						text: dayjs().add(3 * 24 * 60 * 60 * 1000).date(),
						class1: "disSelected",
						class2: "disSelectedText",
					}, {
						date:dayjs().add(4 * 24 * 60 * 60 * 1000).day(),
						text: dayjs().add(4 * 24 * 60 * 60 * 1000).date(),
						class1: "dis:elected",
						class2: "disSelectedText",
					}, {
						date: dayjs().add(5 * 24 * 60 * 60 * 1000).day(),
						text: dayjs().add(5 * 24 * 60 * 60 * 1000).date(),
						class1: "disSelected",
						class2: "disSelectedText",
					}, {
						date: dayjs().add(6 * 24 * 60 * 60 * 1000).day(),
						text: dayjs().add(6 * 24 * 60 * 60 * 1000).date(),
						class1: "disSelected",
						class2: "disSelectedText",
					}
				],
				classes:[
					{
						image:"/static/img2/favicon.png",
						time:"08:30~09:30",
						name:"能量拜日",
						tname:"然然",
						difficulty:2,
						tags:["搏击","跳舞"],
						price:"500.00"
					}
				]
			}
		},
		methods: {
			onClickItem(e) {
				if (this.current !== e.currentIndex) {
					this.current = e.currentIndex
				}
			},
			change(data){
				for (var i = 0; i < this.date.length; i++) {
					this.date[i].class1="disSelected"
					this.date[i].class2="disSelectedText"
				}
				data.class1="selected"
				data.class2="selectedText"
			}
		},
		created() {
			for (var i = 0; i < this.date.length; i++) {
				console.log();
				if (this.date[i].date == 0) {
					this.date[i].date = "周日"
				} else if (this.date[i].date == 1) {
					this.date[i].date = "周一"
				} else if (this.date[i].date == 2) {
					this.date[i].date = "周二"
				} else if (this.date[i].date == 3) {
					this.date[i].date = "周三"
				} else if (this.date[i].date == 4) {
					this.date[i].date = "周四"
				} else if (this.date[i].date == 5) {
					this.date[i].date = "周五"
				} else if(this.date[i].date == 6){
					this.date[i].date = "周六"
				}
			}
		},
		onLoad() {
			getClassList(this.items[this.current]).then(res=>{
				console.log(res);
				this.classes=res.data.data
				for (var i = 0; i < res.data.data.length; i++) {
					res.data.data[i].tags=res.data.data[i].tags.split(",")
					// res.data.data.
				}
			})
		}
	}
</script>

<style>
	.uni-common-mt {
		width: 50%;
		margin: 0 auto;
		font-weight: bold;
	}

	.course-date {
		display: flex;
		justify-content: space-around;
	}

	.course-date-item-date {}

	.course-date-item-text {
		margin: 0.5vh auto;
		width: 5vh;
		height: 5vh;
		text-align: center;
		line-height: 5vh;
	}
	.selected{
		background-color: #15CFCF;
		border-radius: 50%;
		color: white;
	}
	.selectedText{
		color: #15CFCF;
		font-weight: bold;
		
	}
	.disSelectedText{
		color: black;
		font-weight: normal;
	}
	.course-item{
		background-color: white;
		width: 90%;
		margin: 2vh auto;
		height: 22vh;
		position: relative;
	}
	.course-item-time{
		background-image: linear-gradient(to right, #dfdfdf,white);
		width: 23vh;
		color: #15CFCF;
		font-weight: bolder;
		padding-left: 2vh;
		position: absolute;
		top: 0vh;
		left: 0vh;
	}
	.course-item-image{
		width: 10vh;
		height: 10vh;
		position: absolute;
		top: 6vh;
		left: 2vh;
	}
	.course-item-image image{
		width: 100%;
		height: 100%;
		border-radius: 50%;
	}
	.course-item-title{
		position: absolute;
		top: 6vh;
		left: 15vh;
	}
	.course-item-difficulty{
		position: absolute;
		top: 9.2vh;
		left: 23.5vh;
	}
	.course-item-tags{
		display: flex;
		position: absolute;
		left: 15vh;
		top: 12vh;
	}
	.course-item-tags-item{
		border: 0.1vh solid gray;
		margin-right: 1vh;
		font-size: 1.5vh;
		color: #15CFCF;
		border-radius: 0.4vh;
		padding: 0.2vh 1vh;
		border: 1px solid #15CFCF;
	}
	.course-item-tname{
		position: absolute;
		top: 9vh;
		left: 15vh;
		
	}
	.course-item-price{
		position: absolute;
		top: 15vh;
		left: 15vh;
		color: red;
	}
	 .ensure{
		 position: absolute;
		 left: 33vh;
		 top: 13vh;
		 background-color: #15CFCF;
		 width: 16vh;
		 height: 5vh;
		 line-height: 5vh;
		 border-radius: 2vh;
		 text-align: center;
		 
		 color: white;
	 }
	 .ensure-full{
		 position: absolute;
		 left: 33vh;
		 top: 13vh;
		 background-color: red;
		 width: 16vh;
		 height: 5vh;
		 line-height: 5vh;
		 border-radius: 2vh;
		 text-align: center;
		 
		 color: white;
	 }
	
</style>