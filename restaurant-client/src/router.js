import { createRouter, createWebHistory } from "vue-router";

const routes = [
  { path: "/", name: "home", component: () => import("./views/HomeView.vue") },
  {
    path: "/menu",
    name: "menu",
    component: () => import("./views/MenuView.vue"),
  },
  {
    path: "/clients",
    name: "clients",
    component: () => import("./views/ClientsView.vue"),
  },
  {
    path: "/orders",
    name: "orders",
    component: () => import("./views/OrdersView.vue"),
  },
  {
    path: "/:pathMatch(.*)*",
    name: "not-found",
    component: () => import("./views/NotFoundView.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
