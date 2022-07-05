function dropList() {
    document.getElementById("myDropdown").classList.toggle("show");
}
window.onclick = function (event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}
function dropStocksToSell() {
    document.getElementById("dropStockToSell").classList.toggle("show");
}

function dropStocksToBuy() {
    document.getElementById("dropStockToBuy").classList.toggle("show");
}

window.onclick = function (event) {
    if (!event.target.matches('.dropSwapbtn')) {
        var dropdowns = document.getElementsByClassName("dropdownSwap-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

function printSum() {
    let price = document.getElementById("currentPrice").value;
    let amount = document.getElementById("amount").value;
    let value = price * amount;
    let result = value.toFixed(2);
    document.getElementById("tradeValue").value = result;
}

function printAmount() {
    let amount = document.getElementById("buyAmount").value;
    let rate = document.getElementById("rate").value;
    let value = rate * amount;
    let result = value.toFixed(7);
    document.getElementById("swapInput").value = result;
}

