// pages/tempcar/tempcar.js
var form_data;
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    usr_number: null,
    usr_name: null,
    usr_phone: null,

    typearr: [],
    typeindex: 0,
    industryarr: [],
    industryindex: 0,

    driver_name: null,
    driver_phone: null,
    car_number: null,
    reason: null,

    date: '请选择',
    time: '请选择',

    note: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.fetchData()

  },

  fetchData: function () {
    this.setData({
      typearr: ["请选择", "教职工", "学生"],
    })
  },

  // 三种下拉选择写在一起了(删两个)
  bindPickerChange: function (e) { //下拉选择
    const eindex = e.detail.value;
    const name = e.currentTarget.dataset.pickername;
    // this.setData(Object.assign({},this.data,{name:eindex}))
    switch (name) {
      case 'industry':
        this.setData({
          industryindex: eindex
        })
        break;
      case 'usrtype':
        this.setData({
          typeindex: eindex
        });
        this.fetchMajor();
        break;
      case 'job':
        this.setData({
          jobindex: eindex
        })
        break;
      default:
        return
    }
  },

  fetchMajor: function () {
    if (this.data.typeindex == 1) {
      this.setData({
        industryarr: app.globalData.industryarr2,
        industryindex: 0,
      })
    } else if (this.data.typeindex == 2) {
      this.setData({
        industryarr: app.globalData.industryarr1,
        industryindex: 0,
      })
    } else{
      this.setData({
        industryarr: [],
        industryindex: 0,
      })
    } 
  },

  //日期选择器 
  bindDateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      date: e.detail.value
    })
  },
  //时间选择器
  bindTimeChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      time: e.detail.value
    })
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
      usr_phone: e.detail.value.usr_phone,
      car_number: e.detail.value.car_number,
      note: e.detail.value.note,
    })
    var url = "https://www.messj.xyz/api/carappoint/save";
    wx.request({
      url: url,
      method: "POST",
      data: { 
        "type": this.data.typearr[e.detail.value.typeindex],
        "usr_name": e.detail.value.usr_name,
        "usr_number": e.detail.value.usr_number,
        "major": this.data.industryarr[e.detail.value.industryindex],
        "usr_phone": e.detail.value.usr_phone,
        "driver_name": e.detail.value.driver_name,
        "driver_phone": e.detail.value.driver_phone,
        "car_number": e.detail.value.car_number,
        "date": that.data.date,
        "time": that.data.time,
        "reason": e.detail.value.reason,
        "note": e.detail.value.note,
      },
      success: function (res) {
        console.log(res);
        try{
          if (JSON.parse(res.data).code == 200) {
            wx.hideLoading()
            wx.showModal({
              title: '提示',
              content: '提交成功!',
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
        } catch(e) {
          try {
            if (res.data.code == 200) {
              wx.hideLoading()
              wx.showModal({
                title: '提示',
                content: '提交成功!',
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