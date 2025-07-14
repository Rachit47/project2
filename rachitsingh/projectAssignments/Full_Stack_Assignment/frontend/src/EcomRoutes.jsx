import React, { Suspense } from "react";
import { Route, Routes } from "react-router";
import CreateMappingRequest from "./pages/CreateMappingRequest";
import OrdersPage from "./modules/orders/pages/OrdersPage";
import CategoryManagementPage from "./pages/CategoryManagement";
import CreateCategoryRequest from "./components/CreateCategoryRequest";
import ProtectedRoute from "./components/ProtectedRoute";

const Home = React.lazy(() => import("./pages/Home"));
const ViewMappingRequests = React.lazy(() => import("./pages/ViewMappingRequests"));
const Login = React.lazy(() => import("./pages/Login"));
const Signup = React.lazy(() => import("./pages/Signup"));
const ProductApprovalDashboard = React.lazy(() => import("./pages/ProductApprovalDashboard"));
const ViewCategoryRequests = React.lazy(() => import("./components/ViewCategoryRequests"));
const ViewCategories = React.lazy(() => import("./components/ViewCategories"));
const ApproveCategoryRequests = React.lazy(() => import("./components/ApproveCategoryRequest"));

const EcomRoutes = () => {
  return (
    <Suspense fallback={<div className="text-white bg-dark">Loading...</div>}>
      <Routes>
        {/* Public Routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />

        {/* Protected Routes for all authenticated users */}
        <Route element={<ProtectedRoute />}>
          <Route path="/" element={<Home />} />
          <Route path="/orders" element={<OrdersPage />} />
          <Route path="/create-mapping-request" element={<CreateMappingRequest />} />
          <Route path="/view-mapping-requests" element={<ViewMappingRequests />} />
        </Route>

        {/* Protected Routes for Admin/Manager roles only */}
        <Route element={<ProtectedRoute allowedRoles={["ROLE_ADMIN", "ROLE_MANAGER"]} />}>
          <Route path="/product-approval" element={<ProductApprovalDashboard />} />
          <Route path="/category-management" element={<CategoryManagementPage />} />
          <Route path="/create-category-request" element={<CreateCategoryRequest />} />
          <Route path="/view-category-requests" element={<ViewCategoryRequests />} />
          <Route path="/view-categories" element={<ViewCategories />} />
          <Route path="/approve-category-requests" element={<ApproveCategoryRequests />} />
        </Route>
      </Routes>
    </Suspense>
  );
};

export default EcomRoutes;
