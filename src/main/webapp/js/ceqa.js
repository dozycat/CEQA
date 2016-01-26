// 基于准备好的dom，初始化echarts实例

$(function() {
	var q = $("#qq");
	var myChart = echarts.init(document.getElementById('draw'));

	var drawfunc = function(json) {

		if ((json == null) || (json == "")) {
			return;
		}
		if (typeof (json) != "object") {
			var graph = $.parseJSON(json);
		} else {
			var graph = json;
		}
		myChart = echarts.init(document.getElementById('draw'));
		var categories = [ {
			name : '类别'
		}, {
			name : '实体'
		}, {
			name : '属性'
		} ];
		graph.nodes.forEach(function(node) {
			node.itemStyle = null;
			node.value = node.symbolSize;
			node.category = node.modularityClass;
			node.label = {
				normal : {
					show : true
				}
			};
		});
		option = {
			title : {
				text : '',
				subtext : 'http://dozy.me',
				top : 'bottom',
				left : 'middle'
			},
			tooltip : {
				formatter : function(param) {
					if (param.data.value > 0) {
						return param.name;
					} else {
						return param.data.name;
					}
				}
			},
			legend : [ {
				// selectedMode: 'single',
				data : categories.map(function(a) {
					return a.name;
				})
			} ],
			animationDuration : 1500,
			animationEasingUpdate : 'quinticInOut',
			series : [ {
				name : '知识图谱',
				type : 'graph',
				layout : 'circular',
				roam : true,
				data : graph.nodes,
				links : graph.links,
				categories : categories,
				roam : true,
				label : {
					normal : {
						position : 'left',
						formatter : function(param) {
							return param.data.name
						}
					}
				},
				lineStyle : {
					normal : {
						curveness : 0.3
					}
				}
			} ]
		};
		myChart.hideLoading();
		myChart.setOption(option);
	};
	q.keyup(function(event) {
		var q = $("#qq").val();
		// 加特效
		if (q == "支持双卡双待的手机有哪些") {
			myChart = echarts.init(document.getElementById('draw'));
			option = {
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b}: {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : [ '联想P780', '英华OKC152', '金立N73', '联创LC0706',
							'海尔K72', '海信M618', '联想', '华为', '金立', '其他' ]
				},
				series : [ {
					name : '访问来源',
					type : 'pie',
					selectedMode : 'single',
					radius : [ 0, '30%' ],

					label : {
						normal : {
							position : 'inner'
						}
					},
					labelLine : {
						normal : {
							show : false
						}
					},
					data : [ {
						value : 335,
						name : '金立',
						selected : true
					}, {
						value : 600,
						name : '其他',
						selected : true
					}, {
						value : 679,
						name : '华为'
					}, {
						value : 1548,
						name : '联想'
					} ]
				}, {
					name : '產品',
					type : 'pie',
					radius : [ '40%', '55%' ],

					data : [ {
						value : 335,
						name : '联想P780'
					}, {
						value : 335,
						name : '英华OKC152'
					}, {
						value : 335,
						name : '金立N73'
					}, {
						value : 335,
						name : '联创LC0706'
					}, {
						value : 335,
						name : '海尔K72'
					}, {
						value : 335,
						name : '海信M618'
					} ]
				} ]
			};
			myChart.setOption(option);
			return;
		} else if (q == "价格最高的手机是") {
			myChart = echarts.init(document.getElementById('draw'));
			option = {
				tooltip : {
					trigger : 'axis',
					axisPointer : { // 坐标轴指示器，坐标轴触发有效
						type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
					}
				},
				legend : {
					data : [ 'Nokia/诺基亚E62', '摩托罗拉E2', '夏新N88', '诺基亚5800XM' ]
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				xAxis : [ {
					type : 'category',
					data : [ 'Nokia/诺基亚E62', '摩托罗拉E2', '夏新N88', '诺基亚5800XM' ]
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '价格',
					type : 'bar',
					data : [ 3050, 1018, 964, 1026],
					markLine : {
						itemStyle : {
							normal : {
								lineStyle : {
									type : 'dashed'
								}
							}
						},
						data : [ [ {
							type : 'min'
						}, {
							type : 'max'
						} ] ]
					}
				}]
			};
			myChart.setOption(option);
			return;
		}
		{
			$.ajax({
				type : 'GET',
				encoding : "UTF-8",
				dataType : "json",
				contentType : "application/json; charset=UTF-8",
				url : './qa.io',
				data : {
					question : $("#qq").val()
				},
				success : drawfunc
			});
		}
	});
	$.ajax({
		type : 'GET',
		encoding : "UTF-8",
		dataType : "html",
		contentType : "application/json; charset=UTF-8",
		url : './hello.io',
		data : {
			question : "first"
		},
		success : drawfunc
	});
});
