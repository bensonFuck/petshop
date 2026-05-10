document.addEventListener('DOMContentLoaded', function() {
    showSection('dashboard');
    loadStatistics();
    loadCatList();
    loadOrderList();
    loadProductList();
    loadUserList();
});

function showSection(sectionId) {
    document.querySelectorAll('.sidebar-menu a').forEach(function(link) {
        link.classList.remove('active');
    });
    document.querySelectorAll('.section').forEach(function(section) {
        section.classList.remove('active');
    });
    document.querySelector('[href="#' + sectionId + '"]').classList.add('active');
    document.getElementById(sectionId).classList.add('active');
}

function logout() {
    if (confirm('确定要退出登录吗？')) {
        window.location.href = 'login.html';
    }
}

function loadStatistics() {
    document.getElementById('stat-cats').textContent = '12';
    document.getElementById('stat-orders').textContent = '28';
    document.getElementById('stat-users').textContent = '156';
    document.getElementById('stat-revenue').textContent = '¥28,500';
}

function loadCatList() {
    var cats = [
        { id: 1, name: '英短蓝猫', age: '3个月', price: '2800', status: 'available', image: 'https://neeko-copilot.bytedance.net/api/text2image?prompt=british%20shorthair%20blue%20cat%20kitten%20cute%20pet%20photography&image_size=square' },
        { id: 2, name: '波斯猫', age: '4个月', price: '3500', status: 'available', image: 'https://neeko-copilot.bytedance.net/api/text2image?prompt=persian%20cat%20white%20fluffy%20cute%20pet%20photography&image_size=square' },
        { id: 3, name: '布偶猫', age: '2个月', price: '5800', status: 'sold', image: 'https://neeko-copilot.bytedance.net/api/text2image?prompt=ragdoll%20cat%20blue%20eyes%20cute%20pet%20photography&image_size=square' },
        { id: 4, name: '橘猫', age: '5个月', price: '1200', status: 'available', image: 'https://neeko-copilot.bytedance.net/api/text2image?prompt=orange%20tabby%20cat%20cute%20pet%20photography&image_size=square' },
        { id: 5, name: '暹罗猫', age: '3个月', price: '2200', status: 'available', image: 'https://neeko-copilot.bytedance.net/api/text2image?prompt=siamese%20cat%20blue%20eyes%20cute%20pet%20photography&image_size=square' },
        { id: 6, name: '美短', age: '4个月', price: '2600', status: 'sold', image: 'https://neeko-copilot.bytedance.net/api/text2image?prompt=american%20shorthair%20cat%20cute%20pet%20photography&image_size=square' }
    ];
    
    var html = '';
    cats.forEach(function(cat) {
        html += `
            <div class="cat-item">
                <img src="${cat.image}" alt="${cat.name}">
                <div class="cat-info">
                    <h4>${cat.name}</h4>
                    <p>年龄: ${cat.age}</p>
                    <p>价格: ¥${cat.price}</p>
                    <span class="status ${cat.status}">${cat.status === 'available' ? '可领养' : '已售出'}</span>
                </div>
                <div class="cat-actions">
                    <button onclick="editCat(${cat.id})">编辑</button>
                    <button onclick="deleteCat(${cat.id})">删除</button>
                </div>
            </div>
        `;
    });
    document.getElementById('cat-list').innerHTML = html;
}

function loadOrderList() {
    var orders = [
        { id: 'ORD001', user: '张三', product: '英短蓝猫', price: '2800', status: 'pending', date: '2024-01-15' },
        { id: 'ORD002', user: '李四', product: '猫粮套餐', price: '280', status: 'completed', date: '2024-01-14' },
        { id: 'ORD003', user: '王五', product: '寄养服务', price: '500', status: 'pending', date: '2024-01-13' },
        { id: 'ORD004', user: '赵六', product: '布偶猫', price: '5800', status: 'completed', date: '2024-01-12' },
        { id: 'ORD005', user: '钱七', product: '问诊服务', price: '150', status: 'processing', date: '2024-01-11' }
    ];
    
    var html = '';
    orders.forEach(function(order) {
        html += `
            <tr>
                <td>${order.id}</td>
                <td>${order.user}</td>
                <td>${order.product}</td>
                <td>¥${order.price}</td>
                <td><span class="order-status ${order.status}">${getStatusText(order.status)}</span></td>
                <td>${order.date}</td>
                <td>
                    <button onclick="viewOrder(${order.id})">查看</button>
                </td>
            </tr>
        `;
    });
    document.getElementById('order-body').innerHTML = html;
}

function loadUserList() {
    var users = [
        { id: 1, name: '张三', phone: '138****1234', email: 'zhangsan@xxx.com', role: 'user', registerDate: '2024-01-10' },
        { id: 2, name: '李四', phone: '139****5678', email: 'lisi@xxx.com', role: 'user', registerDate: '2024-01-08' },
        { id: 3, name: '王五', phone: '137****9012', email: 'wangwu@xxx.com', role: 'vip', registerDate: '2024-01-05' },
        { id: 4, name: '赵六', phone: '136****3456', email: 'zhaoliu@xxx.com', role: 'user', registerDate: '2024-01-03' },
        { id: 5, name: '钱七', phone: '135****7890', email: 'qianqi@xxx.com', role: 'vip', registerDate: '2024-01-01' }
    ];
    
    var html = '';
    users.forEach(function(user) {
        html += `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.phone}</td>
                <td>${user.email}</td>
                <td><span class="user-role ${user.role}">${user.role === 'vip' ? 'VIP用户' : '普通用户'}</span></td>
                <td>${user.registerDate}</td>
                <td>
                    <button onclick="viewUser(${user.id})">查看</button>
                    <button onclick="deleteUser(${user.id})">删除</button>
                </td>
            </tr>
        `;
    });
    document.getElementById('user-body').innerHTML = html;
}

function getStatusText(status) {
    var texts = {
        'pending': '待处理',
        'processing': '处理中',
        'completed': '已完成'
    };
    return texts[status] || status;
}

function editCat(id) {
    alert('编辑猫咪信息: ID=' + id);
}

function deleteCat(id) {
    if (confirm('确定要删除这只猫咪吗？')) {
        alert('猫咪已删除: ID=' + id);
        loadCatList();
    }
}

function viewOrder(id) {
    alert('查看订单详情: ID=' + id);
}

function viewUser(id) {
    alert('查看用户详情: ID=' + id);
}

function deleteUser(id) {
    if (confirm('确定要删除该用户吗？')) {
        alert('用户已删除: ID=' + id);
        loadUserList();
    }
}

function openAddCatModal() {
    document.getElementById('addCatModal').style.display = 'flex';
}

function closeAddCatModal() {
    document.getElementById('addCatModal').style.display = 'none';
}

function addCat() {
    alert('猫咪添加成功！');
    closeAddCatModal();
    loadCatList();
}

function loadProductList() {
    var products = [
        { id: 1, name: '皇家猫粮 成猫', desc: '400g / 增强免疫力', price: '128', status: 'shelf', image: 'https://images.unsplash.com/photo-1589924691995-400dc9ecc119?w=400&h=400&fit=crop' },
        { id: 2, name: '猫罐头组合装', desc: '6罐装 / 多种口味', price: '68', status: 'shelf', image: 'https://images.unsplash.com/photo-1601758228041-f3b2795255f1?w=400&h=400&fit=crop' },
        { id: 3, name: '智能猫砂盆', desc: '全自动清理', price: '598', status: 'shelf', image: 'https://images.unsplash.com/photo-1535930749574-1399327ce78f?w=400&h=400&fit=crop' },
        { id: 4, name: '猫咪零食礼包', desc: '混合口味 / 营养丰富', price: '45', status: 'shelf', image: 'https://images.unsplash.com/photo-1585067612043-7609bee9e0d8?w=400&h=400&fit=crop' },
        { id: 5, name: '猫爬架豪华版', desc: '多层设计 / 稳固耐用', price: '398', status: 'shelf', image: 'https://images.unsplash.com/photo-1601758174114-e711c0cbaa69?w=400&h=400&fit=crop' },
        { id: 6, name: '冻干鸡胸肉', desc: '高蛋白 / 无添加', price: '88', status: 'shelf', image: 'https://images.unsplash.com/photo-1581889470535-1a43db6bd6e4?w=400&h=400&fit=crop' },
        { id: 7, name: '猫咪梳子', desc: '去毛球专用', price: '35', status: 'shelf', image: 'https://neeko-copilot.bytedance.net/api/text2image?prompt=cat%20grooming%20comb%20brush%20professional%20pet%20grooming%20tool%20product%20photography&image_size=square' },
        { id: 8, name: '猫薄荷玩具', desc: '多种款式 / 逗猫神器', price: '28', status: 'shelf', image: 'https://neeko-copilot.bytedance.net/api/text2image?prompt=cat%20toys%20variety%20feather%20ball%20mouse%20colorful%20product%20photography%20white%20background&image_size=square' }
    ];
    
    var html = '';
    products.forEach(function(product) {
        html += `
            <div class="cat-item">
                <img src="${product.image}" alt="${product.name}">
                <div class="product-info">
                    <h4>${product.name}</h4>
                    <p>${product.desc}</p>
                    <p>价格: ¥${product.price}</p>
                    <span class="status ${product.status}">${product.status === 'shelf' ? '上架中' : '已下架'}</span>
                </div>
                <div class="product-actions">
                    <button onclick="editProduct(${product.id})">编辑</button>
                    <button onclick="${product.status === 'shelf' ? 'unshelfProduct' : 'shelfProduct'}(${product.id})">
                        ${product.status === 'shelf' ? '下架' : '上架'}
                    </button>
                    <button onclick="deleteProduct(${product.id})">删除</button>
                </div>
            </div>
        `;
    });
    document.getElementById('product-list').innerHTML = html;
}

function editProduct(id) {
    alert('编辑商品信息: ID=' + id);
}

function shelfProduct(id) {
    if (confirm('确定要上架该商品吗？')) {
        alert('商品已上架: ID=' + id);
        loadProductList();
    }
}

function unshelfProduct(id) {
    if (confirm('确定要下架该商品吗？')) {
        alert('商品已下架: ID=' + id);
        loadProductList();
    }
}

function deleteProduct(id) {
    if (confirm('确定要删除该商品吗？')) {
        alert('商品已删除: ID=' + id);
        loadProductList();
    }
}

function openAddProductModal() {
    document.getElementById('addProductModal').style.display = 'flex';
}

function closeAddProductModal() {
    document.getElementById('addProductModal').style.display = 'none';
    document.getElementById('product-name').value = '';
    document.getElementById('product-desc').value = '';
    document.getElementById('product-price').value = '';
    document.getElementById('product-image').value = '';
}

function addProduct() {
    var name = document.getElementById('product-name').value;
    var desc = document.getElementById('product-desc').value;
    var price = document.getElementById('product-price').value;
    var image = document.getElementById('product-image').value;
    
    if (!name || !price) {
        alert('请填写商品名称和价格');
        return;
    }
    
    alert('商品添加成功！');
    closeAddProductModal();
    loadProductList();
}