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
		if (q == "价格最高的手机是") {
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
					data : [ 5050, 1018, 964, 1026],
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
	$("#try").click(function(event) {
		$.ajax({
			type : 'GET',
			encoding : "UTF-8",
			dataType : "json",
			contentType : "application/json; charset=UTF-8",
			url : './question.io',
			data : {
				question : $("#qq").val()
			},
			success : function (value){
				$("#res").html("<h3 id='res'>" + value + "<h3");
			}
		});
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
