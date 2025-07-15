import React, { Suspense } from "react";
import { Route, Routes } from "react-router";
import CreateCategoryMappingRequest from "./pages/CreateCategoryMappingRequest";
import OrdersPage from "./pages/OrdersPage";
import CategoryManagementPage from "./pages/CategoryManagement";
import CreateCategoryRequest from "./components/category/CreateCategoryRequest";
import ProtectedRoute from "./components/ProtectedRoute";
import ProductRequestDashboard from "./pages/ProductRequestDashBoard";

const Home = React.lazy(() => import("./pages/Home"));
const ViewCategoryMappingRequests = React.lazy(() =>
  import("./pages/ViewCategoryMappingRequests")
);
const Login = React.lazy(() => import("./pages/Login"));
const Signup = React.lazy(() => import("./pages/Signup"));
const ProductApprovalDashboard = React.lazy(() =>
  import("./pages/ProductApprovalDashboard")
);
const ViewCategoryRequests = React.lazy(() =>
  import("./components/category/ViewCategoryRequests")
);
const ViewCategories = React.lazy(() => import("./components/ViewCategories"));
const ApproveCategoryRequests = React.lazy(() =>
  import("./components/category/ApproveCategoryRequest")
);

const EcomRoutes = () => {
  return (
    <Suspense fallback={<div className="text-white bg-dark">Loading...</div>}>
      <Routes>
        {/* Public Routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        {/* <Route path="/orders" element={<OrdersPage />} />
        <Route path="/product-approval" element={<ProductApprovalDashboard />} /> */}
        {/* Protected Routes for all authenticated users */}
        <Route element={<ProtectedRoute />}>
          <Route path="/" element={<Home />} />
          <Route path="/orders" element={<OrdersPage />} />
        </Route>

        {/* Protected Routes for Admin/Manager roles only */}
        <Route
          element={
            <ProtectedRoute
              allowedRoles={["ROLE_CATEGORY_EXE", "ROLE_MANAGER"]}
            />
          }
        >
          <Route
            path="category/category-management"
            element={<CategoryManagementPage />}
          />
          <Route
            path="category/create-category-request"
            element={<CreateCategoryRequest />}
          />
          <Route
            path="category/view-category-requests"
            element={<ViewCategoryRequests />}
          />
          <Route path="category/view-categories" element={<ViewCategories />} />
          <Route
            path="category/approve-category-requests"
            element={<ApproveCategoryRequests />}
          />

          {/*routes for mapping requests */}
          <Route
            path="categorymapping/create-mapping-request"
            element={<CreateCategoryMappingRequest />}
          />
          <Route
            path="categorymapping/view-mapping-requests"
            element={<ViewCategoryMappingRequests />}
          />
        </Route>

        <Route element={<ProtectedRoute allowedRoles={["ROLE_MANAGER"]} />}>
          <Route path="/product-approval" element={<ProductApprovalDashboard />} />
        </Route>
         {/* <Route element={<ProtectedRoute allowedRoles={["ROLE_PRODUCT_EXE"]} />}>
          <Route path="/product-requests" element={<ProductRequestDashboard />} />
        </Route> */}
        <Route path="/product-requests" element={<ProductRequestDashboard />} />
      </Routes>
    </Suspense>
  );
};

export default EcomRoutes;
