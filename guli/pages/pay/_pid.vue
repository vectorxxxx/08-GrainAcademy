<template>
  <div class="cart py-container">
    <!--主内容-->
    <div class="checkout py-container  pay">
      <div class="checkout-tit">
        <h4 class="fl tit-txt"><span class="success-icon"/><span
          class="success-info">订单提交成功，请您及时付款！订单号：{{ payObj.out_trade_no }}</span>
        </h4>
        <span class="fr"><em class="sui-lead">应付金额：</em><em class="orange money">￥{{ payObj.total_fee }}</em></span>
        <div class="clearfix"/>
      </div>
      <div class="checkout-steps">
        <div class="fl weixin">微信支付</div>
        <div class="fl sao">
          <p class="red">请使用微信扫一扫。</p>
          <div class="fl code">
            <!--            <img id="qrious" src="~/assets/img/erweima.png" alt="">-->
            <!--            <qriously :size="338" value="weixin://wxpay/bizpayurl?pr=LmfQZwfzz"/>-->
            <qriously :value="payObj.code_url" :size="338"/>
            <div class="saosao">
              <p>请使用微信扫一扫</p>
              <p>扫描二维码支付</p>
            </div>

          </div>

        </div>
        <div class="clearfix"/>
        <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->

      </div>
    </div>
  </div>
</template>
<script>
import payApi from '@/api/pay'

export default {
  asyncData({ params, error }) {
    return payApi.createNative(params.pid).then(response => {
      return {
        payObj: response.data.data
      }
    })
  },

  data() {
    return {
      timer: null,
      initQCode: '',
      time1: ''
    }
  },

  mounted() {
    this.timer = setInterval(() => {
      this.queryPayStatus(this.payObj.out_trade_no)
    }, 3000)
  },

  methods: {
    queryPayStatus(orderNo) {
      payApi.queryPayStatus(orderNo).then(response => {
        // 支付成功
        if (response.data.success) {
          // 清除定时器
          clearInterval(this.timer)
          // 消息
          this.$message({
            type: 'success',
            message: '支付成功'
          })
          // 跳转至课程页面
          this.$router.push({ path: '/course/' + this.payObj.course_id })
        }
      })
    }
  }
}
</script>
