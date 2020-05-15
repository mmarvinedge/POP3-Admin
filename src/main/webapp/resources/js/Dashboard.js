function drawVerticalBar(idChart, labels, data) {
    new Chart(document.getElementById(idChart), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: "",
                    fontColor: '#b6c2c9',
                    backgroundColor: '#4dc9f6',
                    data: data
                }
            ]
        },
        options: {
            legend: {display: false, labels: {
                    fontColor: '#b6c2c9'
                }},
            title: {
                display: true,
                text: ''
            },
            responsive: true,
            maintainAspectRatio: false,
            tooltips: {
                callbacks: {
                    label: function (tooltipItem, data) {
                        return data.datasets[0].data[tooltipItem.index].toLocaleString('pt-br', {style: 'currency', currency: 'BRL'});
                    }
                }
            }
        }
    });
}