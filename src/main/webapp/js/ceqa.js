// 基于准备好的dom，初始化echarts实例
$(function(){
    var myChart = echarts.init(document.getElementById('draw'));

    myChart.showLoading();
    $.get('./les-miserables.gexf', function(xml) {
        myChart.hideLoading();

        var graph = echarts.tools.parse(xml);
        var categories = [];
        for (var i = 0; i < 9; i++) {
            categories[i] = {
                name: '类目' + i
            };
        }
        graph.nodes.forEach(function(node) {
            node.itemStyle = null;
            node.value = node.symbolSize;
            node.label.normal.show = node.symbolSize > 30;
            node.category = node.attributes.modularity_class;
        });
        option = {
            title: {
                text: 'Les Miserables',
                subtext: 'Default layout',
                top: 'bottom',
                left: 'right'
            },
            tooltip: {},
            legend: [{
                // selectedMode: 'single',
                data: categories.map(function(a) {
                    return a.name;
                })
            }],
            animationDuration: 1500,
            animationEasingUpdate: 'quinticInOut',
            series: [{
                name: 'Les Miserables',
                type: 'graph',
                layout: 'none',
                data: graph.nodes,
                links: graph.links,
                categories: categories,
                roam: true,
                label: {
                    normal: {
                        position: 'right'
                    }
                },
                lineStyle: {
                    normal: {
                        curveness: 0.3
                    }
                }
            }]
        };

        myChart.setOption(option);
    }, 'xml');
});
