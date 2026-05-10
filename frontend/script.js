let isLoginMode = true;
let cart = [];

document.addEventListener('DOMContentLoaded', function() {
    initNavbar();
    initFilters();
    updateCartDisplay();
});

function initNavbar() {
    window.addEventListener('scroll', function() {
        const nav = document.querySelector('.navbar');
        const container = document.querySelector('.nav-container');
        if (window.scrollY > 50) {
            container.classList.add('scrolled');
        } else {
            container.classList.remove('scrolled');
        }
    });

    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function(e) {
            document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
            this.classList.add('active');
        });
    });
}

function initFilters() {
    const filterBtns = document.querySelectorAll('.filter-btn');
    filterBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            filterBtns.forEach(b => b.classList.remove('active'));
            this.classList.add('active');
        });
    });
}

function filterProducts(category) {
    const products = document.querySelectorAll('.product-card');
    products.forEach(product => {
        if (category === 'all' || product.dataset.category === category) {
            product.classList.remove('hidden');
        } else {
            product.classList.add('hidden');
        }
    });
}

function openLoginModal() {
    document.getElementById('loginModal').classList.add('active');
    resetForm();
}

function closeLoginModal() {
    document.getElementById('loginModal').classList.remove('active');
    resetForm();
}

function switchTab(tab) {
    isLoginMode = tab === 'login';
    const tabs = document.querySelectorAll('.login-tabs .tab-btn');
    const title = document.getElementById('modalTitle');
    const emailGroup = document.getElementById('emailGroup');
    const submitBtn = document.getElementById('submitBtn');

    tabs.forEach(t => t.classList.remove('active'));
    if (tab === 'login') {
        tabs[0].classList.add('active');
        title.textContent = '登录';
        emailGroup.style.display = 'none';
        submitBtn.textContent = '登录';
    } else {
        tabs[1].classList.add('active');
        title.textContent = '注册';
        emailGroup.style.display = 'block';
        submitBtn.textContent = '注册';
    }
}

function handleSubmit() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;

    if (!username || !password) {
        alert('请填写用户名和密码');
        return;
    }

    if (!isLoginMode && !email) {
        alert('请填写邮箱');
        return;
    }

    if (isLoginMode) {
        alert('登录功能开发中');
    } else {
        alert('注册功能开发中');
    }

    closeLoginModal();
}

function resetForm() {
    document.getElementById('username').value = '';
    document.getElementById('password').value = '';
    document.getElementById('email').value = '';
    isLoginMode = true;
    document.querySelectorAll('.login-tabs .tab-btn')[0].classList.add('active');
    document.querySelectorAll('.login-tabs .tab-btn')[1].classList.remove('active');
    document.getElementById('modalTitle').textContent = '登录';
    document.getElementById('emailGroup').style.display = 'none';
    document.getElementById('submitBtn').textContent = '登录';
}

function openCartModal() {
    document.getElementById('cartModal').classList.add('active');
    updateCartDisplay();
}

function closeCartModal() {
    document.getElementById('cartModal').classList.remove('active');
}

function addToCart(name, price, desc = '') {
    const existingItem = cart.find(item => item.name === name);
    if (existingItem) {
        existingItem.quantity++;
        showToast(`${name} 数量+1`, 'success');
    } else {
        cart.push({
            name: name,
            price: price.replace('¥', ''),
            desc: desc,
            quantity: 1
        });
        showToast(`${name} 已加入购物车`, 'success');
    }
    updateCartDisplay();
}

function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.innerHTML = `
        <i class="fas fa-check-circle"></i>
        <span>${message}</span>
    `;
    document.body.appendChild(toast);
    
    setTimeout(() => {
        toast.classList.add('show');
    }, 10);
    
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            document.body.removeChild(toast);
        }, 300);
    }, 2500);
}

function updateCartDisplay() {
    const cartItems = document.getElementById('cartItems');
    const cartTotal = document.getElementById('cartTotal');
    const cartCount = document.querySelector('.cart-count');

    if (cart.length === 0) {
        cartItems.innerHTML = '<div class="cart-empty">购物车为空</div>';
        cartTotal.textContent = '¥0';
        cartCount.textContent = '0';
        return;
    }

    let html = '';
    let total = 0;

    cart.forEach((item, index) => {
        const itemTotal = parseFloat(item.price) * item.quantity;
        total += itemTotal;
        html += `
            <div class="cart-item">
                <div class="cart-item-info">
                    <h4>${item.name}</h4>
                    ${item.desc ? `<p>${item.desc}</p>` : ''}
                </div>
                <div class="cart-item-controls">
                    <div class="quantity-control">
                        <button class="qty-btn" onclick="updateQuantity(${index}, -1)">-</button>
                        <span class="qty-value">${item.quantity}</span>
                        <button class="qty-btn" onclick="updateQuantity(${index}, 1)">+</button>
                    </div>
                    <div class="cart-item-price">¥${itemTotal.toFixed(2)}</div>
                    <button class="remove-btn" onclick="removeFromCart(${index})">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </div>
        `;
    });

    cartItems.innerHTML = html;
    cartTotal.textContent = '¥' + total.toFixed(2);
    cartCount.textContent = cart.reduce((sum, item) => sum + item.quantity, 0);
}

function updateQuantity(index, delta) {
    cart[index].quantity += delta;
    if (cart[index].quantity <= 0) {
        cart.splice(index, 1);
    }
    updateCartDisplay();
}

function removeFromCart(index) {
    cart.splice(index, 1);
    updateCartDisplay();
}

function adoptCat(name) {
    openLoginModal();
}

function checkout() {
    if (cart.length === 0) {
        alert('购物车为空，请先添加商品');
        return;
    }
    alert('结算功能开发中');
    cart = [];
    updateCartDisplay();
    closeCartModal();
}

function toggleMobileMenu() {
    const navLinks = document.querySelector('.nav-links');
    navLinks.style.display = navLinks.style.display === 'flex' ? 'none' : 'flex';
}

document.getElementById('loginModal').addEventListener('click', function(e) {
    if (e.target === this) {
        closeLoginModal();
    }
});

document.getElementById('cartModal').addEventListener('click', function(e) {
    if (e.target === this) {
        closeCartModal();
    }
});