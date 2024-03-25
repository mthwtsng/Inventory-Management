document.addEventListener('DOMContentLoaded', function () {
    fetch('/categories')
        .then(response => response.json())
        .then(categories => {
            const categoryNames = categories.map(category => category.name);
            const categoryQuantities = categories.map(category => category.quantity);

            var ctx = document.getElementById('categoryChart').getContext('2d');
            var categoryChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: categoryNames,
                    datasets: [{
                        data: categoryQuantities,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.6)',
                            'rgba(54, 162, 235, 0.6)',
                            'rgba(255, 206, 86, 0.6)',
                            'rgba(75, 192, 192, 0.6)',
                            'rgba(153, 102, 255, 0.6)',
                            'rgba(255, 159, 64, 0.6)',
                            'rgba(200, 200, 200, 0.6)',
                            'rgba(120, 120, 120, 0.6)',
                            'rgba(255, 140, 0, 0.6)',
                            'rgba(0, 128, 128, 0.6)',
                            'rgba(255, 69, 0, 0.6)',
                            'rgba(255, 182, 193, 0.6)',
                        ],
                    }]
                },
                options: {
                    title: {
                        display: true,
                        text: 'Toy and Clothing Inventory by Quantity'
                    }
                }
            });
            document.getElementById('categoryChart').style.width = '400px';
            document.getElementById('categoryChart').style.height = '400px';
        })
        .catch(error => {
            console.error('Error fetching category data:', error);
        });
});
