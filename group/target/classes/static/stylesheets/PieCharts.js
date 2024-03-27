document.addEventListener('DOMContentLoaded', function () {
    fetch('/toyData')
        .then(response => response.json())
        .then(toys => {
            const toyNames = toys.map(toy => toy.name);
            const toyQuantities = toys.map(toy => toy.quantity);

            var ctx = document.getElementById('toyChart').getContext('2d');
            var toyChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: toyNames,
                    datasets: [{
                        data: toyQuantities,
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
                }
            });
        })
        .catch(error => {
            console.error('Error fetching toy data:', error);
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
                }
            });
        })
        .catch(error => {
            console.error('Error fetching clothing data:', error); 
        });
}

document.addEventListener('DOMContentLoaded', loadClothingChart);
