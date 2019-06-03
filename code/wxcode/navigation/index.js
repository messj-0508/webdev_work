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
    title1:null,
    content1: null,
    title2: null,
    content2: null,
  },
  //固定车证办理
  bindToFixedChoose: function () {
    wx.navigateTo({
      url: '/pages/fixedchoose/index',//选择界面
    })
  },
  //临时车辆预约
  bindToTempCarChoose: function () {
    wx.navigateTo({
      url: '/pages/tempcar/tempcar',
    })
  },
  //临时人员预约
  bindToTempManChoose: function () {
    wx.navigateTo({
      url: '../../pages/tempperson/tempperson',
    })
  },
  onLoad: function () {

    /*
    var that = this
    //显示正在加载
    wx.showToast({
      title: '加载中',
      icon: 'loading'
    });
    //从服务器数据库获取插件数据
    wx.request({
      url: 'https://www.messj.xyz/api/plugincontent/search1',
      success: function (res) {
        console.log(res.data.result[0].content);
        responseOK1 = true;
        that.setData({
          title1:res.data.result[0].title,
          content1: res.data.result[0].content,
        })
      }
    }),
    wx.request({
      url: 'https://www.messj.xyz/api/plugincontent/search2',
      success: function (res) {
        console.log(res.data.result[0].content);
        responseOK2 = true;
        that.setData({
          title2:res.data.result[0].title,
          content2:res.data.result[0].content,
        })
      }
    })
    //关闭加载提示框
    //wx.hideToast();
    */
  },
  onReady: function () {
    console.log('onReady')
    var that = this;
    //获得popup组件
    this.popup1 = this.selectComponent("#popup1");
    this.popup2 = this.selectComponent("#popup2");
  },

  data: {
    targetId: ""
  },
  //显示临时车辆popup
  showPopupCar(e) {
    //console.log("临时车辆" + e.target.id + ", " + this.popup.title);
    this.popup1.showPopup(e.target.id);
  },
  //显示临时人员opup
  showPopupHum(e) {
    //显示正在加载
    this.setData({
      targetId: e.target.id
    })
    //console.log("临时人员" + e.target.id + ", " + this.popup.id);
    this.popup2.showPopup(e.target.id);
  },

  //取消事件
  _error1() {
    console.log('你点击了取消');
    this.popup1.hidePopup();
  },
  //确认事件
  _success1() {
    console.log('你点击了确定');
    this.bindToTempCarChoose();
    this.popup1.hidePopup();
  },  
  //取消事件
  _error2() {
    console.log('你点击了取消');
    this.popup2.hidePopup();
  },
  //确认事件
  _success2() {
    console.log('你点击了确定');
    this.bindToTempManChoose();
    this.popup2.hidePopup();
  },

})