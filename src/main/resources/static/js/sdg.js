document.addEventListener('DOMContentLoaded', function() {
    const sdgContainer = document.querySelector('.sdg-contain');
    const sdgItems = document.querySelectorAll('.sdg-items');

    // Function to adjust layout based on screen size
    function adjustLayout() {
        const containerWidth = sdgContainer.offsetWidth;
        const itemWidth = 300; // Base width of each SDG item
        const gap = 20; // Gap between items

        // Calculate how many items can fit in one row
        const itemsPerRow = Math.floor((containerWidth + gap) / (itemWidth + gap));
        
        // Adjust flex basis based on items per row
        sdgItems.forEach(item => {
            if (itemsPerRow === 1) {
                item.style.flexBasis = '100%';
            } else if (itemsPerRow === 2) {
                item.style.flexBasis = 'calc(50% - 10px)';
            } else {
                item.style.flexBasis = 'calc(33.333% - 14px)';
            }
        });
    }

    // Initial adjustment
    adjustLayout();

    // Adjust on window resize
    window.addEventListener('resize', adjustLayout);
}); 