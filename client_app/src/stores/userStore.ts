import { defineStore } from "pinia";
import { ref } from "vue";

interface User {
  id: number;
  role: string;
  name: string;
  img: string;
  email: string;
}

interface UserStore {
  user: User;
  searchInput: string;
  setUserData: (id:string, role: string, name: string, img: string, email: string) => void;
  deleteUserData: () => void;
}

export const useUserStore = defineStore("userStore", () => {
  const user = ref<User>({
    id: '',
    role: "",
    name: "",
    img: "default",
    email: "",
  });
  const searchInput = ref("");
  const setUserData = (id: string, role: string, name: string, img: string, email: string) => {
    user.value.id = id
    user.value.role = role;
    user.value.name = name;
    user.value.img = img;
    user.value.email = email;
  };
  const deleteUserData = () => {
    user.value.id = '';
    user.value.role = "";
    user.value.name = "";
    user.value.img = "default";
    user.value.email = "";
  };

  return {
    user,
    searchInput,
    setUserData,
    deleteUserData,
  };
});
