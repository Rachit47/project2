import React, { useState } from "react";
import { Container, Tabs, Tab } from "react-bootstrap";
import CreateCategoryRequestForm from "../components/CreateCategoryRequestForm";
import ViewCategoryRequests from "../components/ViewCategoryRequests";
import ApproveCategoryRequests from "../components/ApproveCategoryRequest";
import ViewCategories from "../components/ViewCategories";

const CategoryManagement = () => {
  const [key, setKey] = useState("create");

  return (
    <Container className="mt-4">
      <h2>Category Management</h2>
      <Tabs
        id="category-tabs"
        activeKey={key}
        onSelect={(k) => setKey(k)}
        className="mb-3"
        justify
      >
        <Tab eventKey="create" title="Create Request">
          <CreateCategoryRequestForm />
        </Tab>

        <Tab eventKey="viewRequests" title="View Requests">
          <ViewCategoryRequests />
        </Tab>

        <Tab eventKey="approveRequests" title="Approve Requests">
          <ApproveCategoryRequests />
        </Tab>

        <Tab eventKey="viewCategories" title="View Categories">
          <ViewCategories />
        </Tab>
      </Tabs>
    </Container>
  );
};

export default CategoryManagement;
