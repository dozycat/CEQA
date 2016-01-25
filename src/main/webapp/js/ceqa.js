// 基于准备好的dom，初始化echarts实例

$(function() {
	var q = $("#qq");
	var myChart = echarts.init(document.getElementById('draw'));

	var drawfunc = function(json) {
		// myChart.showLoading();
		if ((json == null) || (json == "")) {
			return;
		}
		if (typeof(json) != "object") {
			var graph = $.parseJSON(json);
		} else {
			var graph = json;
		}

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
