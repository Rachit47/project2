import React, { Suspense } from "react";
import { Route, Routes } from "react-router";
import CreateMappingRequest from "./pages/CreateMappingRequest";
import CategoryManagementPage from "./pages/CategoryManagement";

import CreateCategoryRequest from "./components/CreateCategoryRequest";

const Home = React.lazy(() => import("./pages/Home"));
const ViewMappingRequests = React.lazy(() =>
  import("./pages/ViewMappingRequests")
);

const ViewCategoryRequests = React.lazy(() =>
  import("./components/ViewCategoryRequests")
);
const ViewCategories = React.lazy(() => import("./components/ViewCategories"));
const ApproveCategoryRequests = React.lazy(
  () => import("./components/ApproveCategoryRequest") //
);

const EcomRoutes = () => {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <Routes>
        <Route path="/" element={<Home />} />

        {/* Category Management */}
        <Route
          path="/category-management"
          element={<CategoryManagementPage />}
        />
        <Route
          path="/create-category-request"
          element={<CreateCategoryRequest />}
        />
        <Route
          path="/view-category-requests"
          element={<ViewCategoryRequests />}
        />
        <Route path="/view-categories" element={<ViewCategories />} />
        <Route
          path="/approve-category-requests"
          element={<ApproveCategoryRequests />}
        />

        {/* Mapping Requests */}
        <Route
          path="/create-mapping-request"
          element={<CreateMappingRequest />}
        />
        <Route
          path="/view-mapping-requests"
          element={<ViewMappingRequests />}
        />
      </Routes>
    </Suspense>
  );
};

export default EcomRoutes;

