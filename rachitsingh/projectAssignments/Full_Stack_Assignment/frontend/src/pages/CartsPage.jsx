import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
// import { useAuth } from "../context/AuthContext";

import {
  getCartItems,
  removeCartItem,
  clearCart,
  checkoutCart,
} from "../services/CartService";

const CartPage = () => {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [couponCode, setCouponCode] = useState("");
  const [discount, setDiscount] = useState(0); // For managing coupon discount
  const [address, setAddress] = useState(""); // Add state for address
  const { currentUser } = useAuth();
  const customerId = currentUser.userId;
  const navigate = useNavigate();

  useEffect(() => {
    loadCart();
  }, []);

  const loadCart = async () => {
    try {
      setLoading(true);
      const res = await getCartItems(customerId);

      console.log("Cart response:", res.data);

      let items = [];
      if (Array.isArray(res.data)) {
        items = res.data;
      } else if (res.data && Array.isArray(res.data.items)) {
        items = res.data.items;
      }

      setCartItems(items);
    } catch (error) {
      console.error("Failed to load cart", error);
      alert("Failed to load cart.");
    } finally {
      setLoading(false);
    }
  };

  const handleRemoveItem = async (cartItemId) => {
    try {
      await removeCartItem(cartItemId);
      loadCart();
    } catch (error) {
      console.error("Failed to remove item", error);
      alert("Failed to remove item.");
    }
  };

  const handleClearCart = async () => {
    try {
      await clearCart(customerId);
      loadCart();
    } catch (error) {
      console.error("Failed to clear cart", error);
      alert("Failed to clear cart.");
    }
  };

  const handleCheckout = async () => {
    try {
      if (!address.trim()) {
        alert("Please provide a shipping address.");
        return;
      }
      const checkoutData = { address }; // Sending address along with userId
      const response = await checkoutCart(customerId, checkoutData); // Pass address to API
      alert("Checkout successful! Redirecting to Orders page.");
      navigate("/orders");
    } catch (error) {
      console.error("Checkout failed", error);
      alert("Checkout failed.");
    }
  };

  // Calculate subtotal, tax, and grand total
  const calculateTotals = () => {
    const subtotal = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
    const salesTax = subtotal * 0.1; // Assuming 10% tax rate
    const grandTotal = subtotal + salesTax - discount;
    return { subtotal, salesTax, grandTotal };
  };

  const handleApplyCoupon = () => {
    if (couponCode === "DISCOUNT10") {
      setDiscount(50); // Example discount
    } else {
      alert("Invalid coupon code.");
    }
  };

  const { subtotal, salesTax, grandTotal } = calculateTotals();

  return (
    <div className="p-6 text-white">
      <h1 className="text-2xl font-semibold mb-4 text-black">My Cart</h1>

      {loading ? (
        <p>Loading...</p>
      ) : cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div className="space-y-4">
          {cartItems.map((item) => (
            <div
              key={item.cartItemId}
              className="flex justify-between items-center border p-4 rounded"
            >
              <div className="flex flex-col">
                <p className="text-black font-semibold">{item.productName}</p>
                <p className="text-black font-semibold">Price: ${item.price.toFixed(2)}</p>
                <p className="text-black font-semibold">Quantity: {item.quantity}</p>
                <p className="text-black font-semibold">Total: ${(item.price * item.quantity).toFixed(2)}</p>
              </div>
              <button
                className="bg-red-500 px-3 py-1 rounded"
                onClick={() => handleRemoveItem(item.cartItemId)}
              >
                Remove
              </button>
            </div>
          ))}

          <div className="mt-4">
            <div className="flex justify-between mb-2">
              <p className="text-black font-semibold">Subtotal:</p>
              <p className="text-black font-semibold">${subtotal.toFixed(2)}</p>
            </div>
            <div className="flex justify-between mb-2">
              <p className="text-black font-semibold">Sales Tax (10%):</p>
              <p className="text-black font-semibold">${salesTax.toFixed(2)}</p>
            </div>
            <div className="flex justify-between mb-4">
              <p className="text-black font-semibold"><b>Discount:</b></p>
              <p className="text-black font-semibold">-${discount.toFixed(2)}</p>
            </div>
            <div className="flex justify-between mb-4 font-semibold">
              <p className="text-black font-semibold"><b>Grand Total:</b></p>
              <p className="text-black font-semibold">${grandTotal.toFixed(2)}</p>
            </div>

            {/* <div className="flex space-x-4">
              <input
                type="text"
                placeholder="Enter coupon code"
                className="p-2 rounded w-full"
                value={couponCode}
                onChange={(e) => setCouponCode(e.target.value)}
              />
              <button
                className="bg-blue-500 px-4 py-2 rounded"
                onClick={handleApplyCoupon}
              >
                Apply Coupon
              </button>
            </div> */}
          </div>

          {/* Address input */}
          <div className="mt-4">
            <input
              type="text"
              className="p-2 rounded w-full"
              placeholder="Enter shipping address"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
            />
          </div>

          <div className="mt-4 space-x-4">
            <button
              className="bg-yellow-500 px-4 py-2 rounded"
              onClick={handleClearCart}
            >
              Clear Cart
            </button>
            <button
              className="bg-green-500 px-4 py-2 rounded"
              onClick={handleCheckout}
            >
              Checkout
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default CartPage;

