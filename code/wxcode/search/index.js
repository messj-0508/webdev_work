// pages/search/index.js
var result;
var result_content;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    usr_number: null,
    usr_name: null,
    car_number: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  //----------------------点击提交按钮---------------------------------
  formSubmit: function (e) {
    var that = this;
    wx.showLoading({
      title: '正在提交中',
    })
    this.setData({
      usr_number: e.detail.value.usr_number,
      usr_name: e.detail.value.usr_name,
      car_number: e.detail.value.car_number,
    })
    var url = "https://www.messj.xyz/api/apply/search";
    wx.request({
      url: url,
      method: "POST",
      data: {
        "usr_name": e.detail.value.usr_name,
        "usr_number": e.detail.value.usr_number,
        "car_number": e.detail.value.car_number,
      },
      success: function (res) {
        console.log(res);
        try {
          if (JSON.parse(res.data).code == 200) {
            wx.hideLoading()
            result = JSON.parse(res.data).result;
            if (result == 0) { 
              result_content = "不存在该申请"
            } else if (result == 1) {
              result_content = "等待审核中！"
            } else if (result == 2) {
              result_content = "初步审核通过"
            } else if (result == 3) {
              result_content = "初步审核未通过"
            } else {
              result_content = "系统开了个小差"
            }
            wx.showModal({
              title: '提示',
              content: result_content,
              success: function (res) {
                that.onLoad()
                wx.navigateBack({
                  delta: 2
                })
              }
            })
          } else if (JSON.parse(res.data).code == 102) {
            wx.hideLoading();
            wx.showModal({
              title: '提示',
              content: '该申请已记录，请勿重复申请!',
            })
          } else {
            wx.hideLoading();
            wx.showModal({
              title: '提示',
              content: '提交信息有误,请重新提交!',
            })
          }
        } catch (e) {
          try {
            if (res.data.code == 200) {
              result = res.data.result;
              if (result == 0) {
                result_content = "不存在该申请"
              } else if (result == 1) {
                result_content = "等待审核中！"
              } else if (result == 2) {
                result_content = "初步审核通过"
              } else if (result == 3) {
                result_content = "初步审核未通过"
              } else {
                result_content = "系统开了个小差"
              }
              wx.hideLoading()
              wx.showModal({
                title: '提示',
                content: result_content,
                success: function (res) {
                  that.onLoad()
                  wx.navigateBack({
                    delta: 2
                  })
                }
              })
            } else if (res.data.code == 102) {
              wx.hideLoading();
              wx.showModal({
                title: '提示',
                content: '该申请已记录，请勿重复申请!',
              })
            } else {
              wx.hideLoading();
              wx.showModal({
                title: '提示',
                content: '提交信息有误,请重新提交!',
              })
            }
          } catch (e) {
            wx.hideLoading();
            wx.showModal({
              title: '提示',
              content: '程序出了个小差,请重新提交!',
            })
          }
        }
      }
    })
  },
  //---------------------------------------------------------------------------------
  
})