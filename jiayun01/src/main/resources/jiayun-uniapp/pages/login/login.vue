<template>
	<view class="login-container">
		<view class="background-wrapper">
			<image class="background" src="/static/login.png" mode="aspectFill"></image>
			<view class="overlay"></view>
		</view>
		
		<view class="login-content">
			<view class="login-header">
				<view class="brand-name">潮汐健身房</view>
				<view class="brand-slogan">Tidal Fitness · 开启健康生活</view>
			</view>
			
			<view class="login-form">
				<view class="form-header">
					<view class="form-title">欢迎登录</view>
					<view class="form-subtitle">登录后享受更多健身服务</view>
				</view>
				
				<view class="form-item">
					<view class="input-wrapper">
						<view class="input-icon">
							<text class="iconfont">📱</text>
						</view>
						<input class="input" type="number" v-model="loginForm.phone" placeholder="请输入手机号" maxlength="11" placeholder-class="placeholder" />
					</view>
				</view>
				
				<view class="form-item">
					<view class="input-wrapper">
						<view class="input-icon">
							<text class="iconfont">🔐</text>
						</view>
						<input class="input" placeholder="请输入验证码" type="number" v-model="loginForm.code" maxlength="6" placeholder-class="placeholder" />
						<view class="code-btn" :class="{'disabled': sendMsg!='获取验证码'}" @click="getCode()">
							{{sendMsg}}
						</view>
					</view>
				</view>
				
				<view class="user-type">
					<text class="type-label">登录身份</text>
					<view class="type-options">
						<view class="type-item" :class="{'active': type=='会员'}" @click="type='会员'">
							<text class="type-icon">👤</text>
							<text class="type-text">会员</text>
						</view>
						<view class="type-item" :class="{'active': type=='老师'}" @click="type='老师'">
							<text class="type-icon">👨‍🏫</text>
							<text class="type-text">老师</text>
						</view>
					</view>
				</view>
				
				<view class="login-button" @click="login()">
					<text class="btn-text">登 录</text>
					<text class="btn-arrow">→</text>
				</view>
				
				<view class="form-footer">
					<text class="footer-text">登录即表示同意</text>
					<text class="footer-link">《用户协议》</text>
					<text class="footer-text">和</text>
					<text class="footer-link">《隐私政策》</text>
				</view>
			</view>
		</view>
		
		<view>
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	import { login } from '../../api/login'
	import { getCode } from '../../api/user'
	export default {
		data() {
			return {
				loginForm: {
					phone: "",
					code: ""
				},
				messageText:"",
				msgType:"",
				sendMsg:"获取验证码",
				type:"会员"
			}
		},

		methods: {
			login() {
				var _this = this
				if(!this.loginForm.phone || !this.loginForm.code){
					this.msgType="error"
					this.messageText = "请输入手机号和验证码"
					this.$refs.message.open()
					return
				}
				if(!/^1[3-9]\d{9}$/.test(this.loginForm.phone)){
					this.msgType="error"
					this.messageText = "请输入正确的手机号"
					this.$refs.message.open()
					return
				}
				login(this.loginForm.phone,this.loginForm.code,this.type).then(res=>{
					if(res.data.code==0){
						this.msgType="error"
						this.messageText = res.data.msg
						this.$refs.message.open()
					}else{
						this.msgType="success"
						this.messageText = "登录成功"
						this.$refs.message.open()
						setTimeout(function aa(){
							uni.setStorageSync("auth",res.data.data.id)
							uni.switchTab({
								url:'/pages/index/index'
							})
						},2000)
					}
				})
			},
			getCode() {
				if(this.sendMsg!='获取验证码') return
				if(!this.loginForm.phone){
					this.msgType="error"
					this.messageText = "请输入手机号"
					this.$refs.message.open()
					return
				}
				if(!/^1[3-9]\d{9}$/.test(this.loginForm.phone)){
					this.msgType="error"
					this.messageText = "请输入正确的手机号"
					this.$refs.message.open()
					return
				}
				var param={
					...this.loginForm
				}
				getCode(param).then(res=>{
					if(res.data.code==1){
						uni.showToast({
							title:"验证码发送成功",
							icon:'success',
						})
						var seconds=60	
						var timer = setInterval(() => {
							seconds--
							this.sendMsg=seconds+"s后重发"
							if(seconds==0){
								clearInterval(timer)
								timer=null
								this.sendMsg="获取验证码"
							}
						}, 1000)
					}else{
						uni.showToast({
							title:res.data.msg,
							icon:"error"
						})
					}
				})
			}
		},
		onLoad() {

		}
	}
</script>

<style lang="scss">
page {
	height: 100%;
	overflow: hidden;
}

.login-container {
	position: relative;
	width: 100%;
	height: 100%;
}

.background-wrapper {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}

.background {
	width: 100%;
	height: 100%;
}

.overlay {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: linear-gradient(180deg, 
		rgba(59, 130, 246, 0.85) 0%, 
		rgba(16, 185, 129, 0.75) 50%,
		rgba(59, 130, 246, 0.9) 100%);
}

.login-content {
	position: relative;
	z-index: 1;
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	padding: 0 40rpx;
	box-sizing: border-box;
}

.login-header {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding-top: 100rpx;
	margin-bottom: 60rpx;
}

.logo-wrapper {
	width: 120rpx;
	height: 120rpx;
	background: rgba(255, 255, 255, 0.2);
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 24rpx;
	backdrop-filter: blur(10px);
	border: 2rpx solid rgba(255, 255, 255, 0.3);
}

.logo {
	width: 80rpx;
	height: 80rpx;
}

.brand-name {
	font-size: 52rpx;
	font-weight: bold;
	color: #fff;
	margin-bottom: 12rpx;
	text-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.2);
	letter-spacing: 6rpx;
}

.brand-slogan {
	font-size: 24rpx;
	color: rgba(255, 255, 255, 0.9);
	letter-spacing: 2rpx;
}

.login-form {
	background: rgba(255, 255, 255, 0.98);
	border-radius: 32rpx;
	padding: 48rpx 36rpx;
	box-shadow: 0 20rpx 60rpx rgba(0, 0, 0, 0.15);
	backdrop-filter: blur(20px);
}

.form-header {
	text-align: center;
	margin-bottom: 40rpx;
}

.form-title {
	font-size: 40rpx;
	font-weight: bold;
	color: #1E293B;
	margin-bottom: 12rpx;
}

.form-subtitle {
	font-size: 26rpx;
	color: #64748B;
}

.form-item {
	margin-bottom: 28rpx;
}

.input-wrapper {
	display: flex;
	align-items: center;
	background: linear-gradient(135deg, #F8FAFC 0%, #F1F5F9 100%);
	border-radius: 20rpx;
	padding: 0 24rpx;
	height: 100rpx;
	border: 2rpx solid transparent;
	transition: all 0.3s ease;
}

.input-wrapper:focus-within {
	background: #fff;
	border-color: #3B82F6;
	box-shadow: 0 0 0 6rpx rgba(59, 130, 246, 0.1);
}

.input-icon {
	width: 48rpx;
	height: 48rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 16rpx;
	font-size: 36rpx;
}

.input {
	flex: 1;
	font-size: 30rpx;
	color: #1E293B;
	height: 100%;
}

.placeholder {
	color: #94A3B8;
}

.code-btn {
	font-size: 26rpx;
	padding: 18rpx 28rpx;
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	color: #fff;
	border-radius: 14rpx;
	white-space: nowrap;
	font-weight: 600;
	box-shadow: 0 4rpx 12rpx rgba(59, 130, 246, 0.3);
	transition: all 0.3s;
}

.code-btn:active {
	transform: scale(0.95);
}

.code-btn.disabled {
	background: linear-gradient(135deg, #CBD5E1 0%, #94A3B8 100%);
	box-shadow: none;
}

.user-type {
	display: flex;
	align-items: center;
	justify-content: space-between;
	margin: 32rpx 0;
	padding: 0 8rpx;
}

.type-label {
	font-size: 28rpx;
	color: #64748B;
	font-weight: 500;
}

.type-options {
	display: flex;
	gap: 20rpx;
}

.type-item {
	display: flex;
	align-items: center;
	padding: 16rpx 28rpx;
	border-radius: 12rpx;
	background: linear-gradient(135deg, #F8FAFC 0%, #F1F5F9 100%);
	border: 2rpx solid #E2E8F0;
	transition: all 0.3s;
}

.type-item.active {
	background: linear-gradient(135deg, #3B82F6 0%, #10B981 100%);
	border-color: transparent;
	box-shadow: 0 4rpx 12rpx rgba(59, 130, 246, 0.3);
}

.type-icon {
	font-size: 28rpx;
	margin-right: 8rpx;
}

.type-text {
	font-size: 28rpx;
	color: #64748B;
	font-weight: 500;
}

.type-item.active .type-text {
	color: #fff;
}

.login-button {
	width: 100%;
	height: 108rpx;
	background: linear-gradient(135deg, #F97316 0%, #FB923C 50%, #F97316 100%);
	background-size: 200% 100%;
	border-radius: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: 0 8rpx 32rpx rgba(249, 115, 22, 0.4);
	margin-top: 20rpx;
	animation: gradientMove 3s ease infinite;
}

@keyframes gradientMove {
	0% { background-position: 0% 50%; }
	50% { background-position: 100% 50%; }
	100% { background-position: 0% 50%; }
}

.login-button:active {
	transform: scale(0.98);
	box-shadow: 0 4rpx 16rpx rgba(249, 115, 22, 0.4);
}

.btn-text {
	font-size: 36rpx;
	font-weight: bold;
	color: #fff;
	letter-spacing: 12rpx;
}

.btn-arrow {
	font-size: 36rpx;
	color: #fff;
	margin-left: 16rpx;
	transition: transform 0.3s;
}

.login-button:active .btn-arrow {
	transform: translateX(8rpx);
}

.form-footer {
	display: flex;
	justify-content: center;
	align-items: center;
	margin-top: 32rpx;
	flex-wrap: wrap;
}

.footer-text {
	font-size: 24rpx;
	color: #94A3B8;
}

.footer-link {
	font-size: 24rpx;
	color: #3B82F6;
}
</style>
