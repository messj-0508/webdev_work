//index.js
//获取应用实例
var app = getApp();
//临时车辆的数据
var title_from_web1;
var content_from_web1;
var responseOK1 = false;
//临时人员的数据
var title_from_web2;
var content_from_web2;
var responseOK2 = false;
Page({

  data: {

  },
  
  onLoad: function () {
    //显示正在加载
    wx.showToast({
      title: '加载中',
      icon: 'loading'
    });
    console.log('onLoad')
    var that = this
    wx.request({
      url: 'https://www.messj.xyz/api/getdata/all',
      success: function (res) {
        console.log(res.data);
        app.globalData.industryarr1 = res.data.major;
        app.globalData.industryarr2 = res.data.depart;
        app.globalData.plugin_all = res.data.plugin;
        that.setData({
          title1: app.globalData.plugin_all[1].title,
          content1: app.globalData.plugin_all[1].content,
          title2: app.globalData.plugin_all[2].title,
          content2: app.globalData.plugin_all[2].content,
        })
      }
    })
    wx.hideToast();
  },
  onReady: function () {
    console.log('onReady')
  },

  data: {
    targetId: ""
  },

  verifyStudent: function (e) {
    var detail = e.detail;
    var code = detail.code;
    var message = detail.message;

    if (code == 0) {
      var student = detail.student;

      var cardNumber = student.card_number; // 学号
      var name = student.name; // 姓名
      var weixiaoStuId = student.weixiao_stu_id; // 微校学生id
      var identityType = student.identity_type;// 身份类型，1为其他，2为学生，3为教职工，4为校友
      var telephone = student.telephone;// 手机号（需申请）
    }
    console.log(e);
    wx.switchTab({ url: '/pages/navigation/index' });
  },

  loginCalcel: function (e) {
    var that = this;
    that.selectComponent("#component1").hideToast();
  },
})