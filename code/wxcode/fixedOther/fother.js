var image_belong;
var form_data;
var i = 0
Page({
  data: {
    usr_number: null,
    usr_name: null,

    industryarr: [],
    industryindex: 0,
    hasfinancing: true,  //行驶证驾驶证是否一致

    usr_phone: null,
    car_number: null,
    car_owner: null,

    note: null,

    evalList: [{ tempFilePaths: [null, null, null], imgList: [] }],
  },
  onLoad: function () {
    this.fetchData()
  },

  onShow: function () {
    i = 0;
  },

  fetchData: function () {
    this.setData({
      industryarr: ["请选择", "部门1", "部门2", "部门3", "部门4", "部门5"],
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
      case 'status':
        this.setData({
          statusindex: eindex
        })
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
  //选择驾驶证和行驶证是否一致
  setFinance: function (e) {
    this.setData({
      hasfinancing: e.detail.value == "一致" ? true : false
    })
  },

  //提交按钮
  applySubmit: function () {
    wx.navigateTo({
      url: '../../index/index'
    })
  },

  //添加图片--------------------------------------------------------------------------
  joinPicture: function (e) {
    var index = e.currentTarget.dataset.index;
    var evalList = this.data.evalList;
    var that = this;
    wx.showActionSheet({
      itemList: ["从相册中选择", "拍照"],
      itemColor: "#f7982a",
      success: function (res) {
        if (!res.cancel) {
          if (res.tapIndex == 0) {
            that.chooseWxImage("album", index);
          } else if (res.tapIndex == 1) {
            that.chooseWxImage("camera", index);
          }
        }
      }
    })
  },

  chooseWxImage: function (type, index) {
    var that = this;
    var evalList = this.data.evalList;
    wx.chooseImage({
      count: 1,
      sizeType: ["original", "compressed"],
      sourceType: [type],
      success: function (res) {
        evalList[0].tempFilePaths[index] = res.tempFilePaths[0];
        that.setData({
          evalList: evalList
        })
      },
    })
  },

  //删除图片--------------------------------------------------------------------------
  clearImg: function (e) {
    var index = e.currentTarget.dataset.index;
    var evalList = this.data.evalList;
    evalList[0].tempFilePaths[index] = null;
    this.setData({
      evalList: evalList
    })
  },

  //----------------------点击提交按钮---------------------------------
  formSubmit: function (e) {
    if (e.detail.value.usr_number == '') {
      wx.showToast({
        title: '请填写职工号',
        icon: 'none',
      })
    } else if (e.detail.value.usr_name == '') {
      wx.showToast({
        title: '请填写姓名',
        icon: 'none',
      })
    } else if (e.detail.value.industryindex == 0) {
      wx.showToast({
        title: '请选择部门',
        icon: 'none',
      })
    } else if (e.detail.value.usr_phone == '') {
      wx.showToast({
        title: '请填写联系电话',
        icon: 'none',
      })
    } else if (e.detail.value.car_number == '') {
      wx.showToast({
        title: '请填写车牌号码',
        icon: 'none',
      })
    } else if (e.detail.value.car_owner == '') {
      wx.showToast({
        title: '请填写车辆所有者',
        icon: 'none',
      })
    } else if (this.data.evalList[0].tempFilePaths[0] == null) {
      wx.showToast({
        title: '请上传行驶证照片',
        icon: 'none',
      })
    } else if (this.data.evalList[0].tempFilePaths[1] == null) {
      wx.showToast({
        title: '请上传驾驶证照片',
        icon: 'none',
      })
    } else if ((!this.data.hasfinancing) && (this.data.evalList[0].tempFilePaths[2] == null)) {
      wx.showToast({
        title: '请上传附加证明材料',
        icon: 'none',
      })
    } else {
      form_data = {
        "type": "other",
        "usr_number": e.detail.value.usr_number,
        "usr_name": e.detail.value.usr_name,
        "major": this.data.industryarr[e.detail.value.industryindex],
        "usr_phone": e.detail.value.usr_phone,
        "car_number": e.detail.value.car_number,
        "car_owner": e.detail.value.car_owner,
        "note": e.detail.value.note,
        "step": 0,
        "status": 0,
        "image_belong": null
      };
      wx.showLoading({
        title: '正在提交中',
      })
      this.btn_up()
    }
  },
  btn_up: function (e) {
    var that = this;
    switch (i) {
      case 0:
        image_belong = 'car_card';
        break;
      case 1:
        image_belong = 'usr_card';
        break;
      default:
        image_belong = 'other_img';

    }
    form_data.image_belong = image_belong;
    form_data.step = i;
    if (i == 3 || that.data.evalList[0].tempFilePaths[i + 1] == null) {
      form_data.status = 1;
    }
    var url = "https://www.messj.xyz/api/apply/save";
    wx.uploadFile({
      url: url,
      filePath: that.data.evalList[0].tempFilePaths[i],
      name: 'image',
      formData: form_data,
      success: function (res) {
        i++;
        console.log(res.data);
        try {
          if (JSON.parse(res.data).code == 200) {
            if (that.data.evalList[0].tempFilePaths[i] == null) {
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
            } else {
              that.btn_up()
            }
          } else if (JSON.parse(res.data).code == 102) {
            wx.hideLoading();
            i = 0;
            wx.showModal({
              title: '提示',
              content: '该申请已记录，请勿重复申请!',
            })
          } else if (JSON.parse(res.data).code == 103) {
            wx.hideLoading();
            i = 0;
            wx.showModal({
              title: '提示',
              content: '该功能暂不对服务商用户开放',
            })
          } else {
            wx.hideLoading();
            i = 0;
            wx.showModal({
              title: '提示',
              content: '提交信息有误,请重新提交!',
            })
          }
        } catch (e) {
          try {
            if (res.data.code == 200) {
              if (that.data.evalList[0].tempFilePaths[i] == null) {
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
              } else {
                that.btn_up()
              }
            } else if (res.data.code == 102) {
              wx.hideLoading();
              i = 0;
              wx.showModal({
                title: '提示',
                content: '该申请已记录，请勿重复申请!',
              })
            } else if (res.data.code == 103) {
              wx.hideLoading();
              i = 0;
              wx.showModal({
                title: '提示',
                content: '该功能暂不对服务商用户开放',
              })
            }  else {
              wx.hideLoading();
              i = 0;
              wx.showModal({
                title: '提示',
                content: '提交信息有误,请重新提交!',
              })
            }
          } catch (e) {
            wx.hideLoading();
            i = 0;
            wx.showModal({
              title: '提示',
              content: '程序出了个小差,请重新提交!',
            })
          }
        }

      },
      fail: function (e) {
        wx.hideLoading();
        i = 0;
        wx.showModal({
          title: '提示',
          content: '服务器未响应,请重新提交!',
        })
      },
    })
  }
  //------------------------------------------------------------------------
})
