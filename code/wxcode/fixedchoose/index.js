//index.js
//获取应用实例
var app = getApp();
var title_from_web;
var content_from_web;
var responseOK =false;
Page({
  data: {
    tempFilePaths: "",
    title_from_web: null,
    content_from_web: null,
  },
  //教职工车辆
  bindToFTeacher: function () {
    wx.navigateTo({
      url: '/pages/fixed/fixedTeacher/fteacher',
    })
  },
  //学生车辆
  bindToFStudent: function () {
    wx.navigateTo({
      url: '/pages/fixed/fixedStu/fstudent',
    })
  },
  //服务商车辆
  bindToFOther: function () {
    wx.navigateTo({
      url: '/pages/fixed/fixedOther/fother',
    })
  },

  onLoad: function () {
    console.log('onLoad')
    var that = this;
    title_from_web = app.globalData.plugin_all[0].title;
    content_from_web = app.globalData.plugin_all[0].content;
    responseOK = true;
    /*
    wx.request({
      url: 'https://www.messj.xyz/api/plugincontent/search',
      success: function (res) {
        console.log(res.data.result[0].content);
        responseOK = true;
        title_from_web = res.data.result[0].title;
        content_from_web = res.data.result[0].content;
      }
    })
    */
    //调用应用实例的方法获取全局数据
  },
  onReady: function () {
    //获得popup组件
    this.popup = this.selectComponent("#popup");
  },
  data: {
    targetId:""
  },
  showPopup(e) {
    if(responseOK){
    this.setData({
      targetId: e.target.id,
      title:title_from_web,
      content:content_from_web,
    })
    }
    else{
      this.setData({
        title: "网络有点问题",
        content: "请退出小程序后重试",
      })
    }
    this.popup.showPopup(e.target.id);
  },
  //取消事件
  _error() {
    console.log('你点击了取消');
    this.popup.hidePopup();
  },
  //确认事件
  _success() {
    console.log('你点击了确定');
    switch (this.data.targetId) {
      case "btn_teacher":
        this.bindToFTeacher();
        break;
      case "btn_student":
        this.bindToFStudent();
        break;
      case "btn_other":
        this.bindToFOther();
        break;
      default:
        break;
    }

    this.popup.hidePopup();
  },
})