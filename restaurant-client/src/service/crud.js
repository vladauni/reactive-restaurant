import http from "@/http-common";

export default function createCrudService(path) {
  return {
    findAll() {
      return http.get(path);
    },
    findById(id) {
      return http.get(`${path}/${id}`);
    },
    save(data) {
      return http.post(path, data);
    },
    update(id, data) {
      return http.put(`${path}/${id}`, data);
    },
    delete(id) {
      return http.delete(`${path}/${id}`);
    },
  };
}
