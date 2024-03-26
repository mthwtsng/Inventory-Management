document.addEventListener('DOMContentLoaded', function () {
    fetch('/categoryData') // Fetch data from the endpoint
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
                    },
                    responsive: false, // Prevents resizing of chart
                    maintainAspectRatio: false, // Doesn't maintain aspect ratio
                    width: 400, // Set width
                    height: 400 // Set height
                }
            });
        })
        .catch(error => {
            console.error('Error fetching category data:', error);
        });
});

function loadClothingChart() {
    fetch('/clothingData') // Fetch data from the endpoint
        .then(response => response.json())
        .then(clothings => {
            const clothingType = clothings.map(clothing => clothing.type); // Change 'category' to 'clothing'
            const clothingQuantities = clothings.map(clothing => clothing.quantity); // Change 'category' to 'clothing'

            var ctx = document.getElementById('clothingChart').getContext('2d');
            var clothingChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: clothingType,
                    datasets: [{
                        data: clothingQuantities,
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
                    },
                    responsive: false, // Prevents resizing of chart
                    maintainAspectRatio: false, // Doesn't maintain aspect ratio
                    width: 100, // Set width
                    height: 100 // Set height
                }
            });

            // Set the canvas size directly within Chart.js options
            clothingChart.canvas.parentNode.style.width = '100px';
            clothingChart.canvas.parentNode.style.height = '100px';
        })
        .catch(error => {
            console.error('Error fetching clothing data:', error); // Change 'category' to 'clothing'
        });
}

document.addEventListener('DOMContentLoaded', loadClothingChart);
