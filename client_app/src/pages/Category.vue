 <template>
  <div>
    <div
      class="liner-container mb-6 mt-5 flex justify-center border-b-2 border-gray"
    >
      <h1
        v-if="categories && categories[0]"
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        {{ categories[0].title }}
      </h1>
    </div>
    <div class="tabs" v-if="categories && categories[0]">
      <a
        class="tab tab-lg tab-lifted font-semibold"
        @click="getAllProduct()"
        :class="{ 'tab-active': active === '' }"
        >Все продукты</a
      >
      <a
        v-for="subcategory in categories[0].subcategoryDtos"
        :key="subcategory.id"
        class="tab tab-lg tab-lifted font-semibold"
        @click="getSubcategory( subcategory.titleUrl)"
        :class="{ 'tab-active': active === subcategory.titleUrl }"
        >{{ subcategory.title }}</a
      >
    </div>
    <div
      v-if="allProduct && allProduct[0]"
      class="w-full bg-white rounded-b-lg border border-t-0 rounded-tr-lg xl:p-0"
    >
      <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
        <div class="grid grid-cols-12 gap-5">
          <div
            class="col-span-12 flex md:col-span-6 lg:col-span-4"
            v-for="product in allProduct"
            :key="product.id"
          >
            <CardItem :product="product"></CardItem>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang='ts'>
import CardItem from "../components/CardItem.vue";
import { onMounted, watch, ref } from "vue";
import { getProductById, Product } from "../hooks/useProduct";
import { getProductBySubcategoryId, getProductBySubcategorytitleUrl } from "../hooks/useSubcategory";
import {
  getСategoryFindByTitle,
  getСategoryProductById,
  Category,
} from "../hooks/useCategory";
import { useRoute } from "vue-router";

let active = ref("");

const router = useRoute();
let categories = ref<Category>({
  id: 0,
  title: "",
  titleUrl: "",
  subcategoryDtos: [],
  description: "",
  imageUrl: "",
});

let allProduct = ref<Product>({
  id: 0,
  title: "",
  oldPrice: 0,
  newProduct: false,
  hotProduct: false,
  price: 0,
  description: "",
  quantity: 0,
  weight: 0,
  imageUrl: "",
});

const calculateDiscount = (price: number, oldPrice: number) => {
  return Math.round(100 - (price / oldPrice) * 100);
};

const getSubcategory = async (subTitle: string) => {
  active.value = subTitle;
  const responseProduct = await getProductBySubcategorytitleUrl(subTitle);
  allProduct.value = responseProduct.products;
  const currentUrl = window.location.href;
  let trimmedUrl = currentUrl.split("/").slice(0, 5).join("/");
  const newUrl = trimmedUrl + "/" + subTitle;
  window.history.pushState({ path: newUrl }, "", newUrl);
};

const getAllProduct = async () => {
  active.value = "";
  const currentUrl = window.location.href;
  let trimmedUrl = currentUrl.split("/").slice(0, 5).join("/");
  const newUrl = trimmedUrl;
  window.history.pushState({ path: newUrl }, "", newUrl);
};

onMounted(async () => {
  const currentUrl = window.location.href;
  let trimmedUrl = currentUrl.split("/");

  let categoryName: string;
  if (typeof router.params.categoryName === "string")
    categoryName = router.params.categoryName;
  const response = await getСategoryFindByTitle(categoryName);
  categories.value = { ...response };
  if (trimmedUrl.length === 6) {
    active.value = trimmedUrl[5];
    getSubcategory(active.value)
  } else {
  const responseProduct = await getСategoryProductById(categories.value[0].id);
  allProduct.value = responseProduct.productDtos;}
});
watch(
  () => router.params.categoryName,
  async (newValue) => {
    if (typeof newValue === "string") {
      const response = await getСategoryFindByTitle(newValue);
      categories.value = { ...response };
      const responseProduct = await getСategoryProductById(
        categories.value[0].id
      );
      allProduct.value = responseProduct.productDtos;
    }
  }
);
</script>
<style scoped></style>