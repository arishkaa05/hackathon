 <template>
  <div>
    <div
      class="liner-container mb-6 mt-5 flex justify-center border-b-2 border-gray"
    >
      <h1
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        Продукты
      </h1>
    </div>

    <div class="grid grid-cols-12 gap-5" v-if="products && products.length > 0">
      <div
        class="col-span-12 flex md:col-span-6 lg:col-span-4"
        v-for="product in products"
        :key="product"
      > 
        <CardItem :product="product"></CardItem>
      </div>
    </div>
  </div>
</template>


<script setup lang="ts">
import { onMounted, ref } from "vue";

import CardItem from "../components/CardItem.vue";
import { getProductPagination, Product } from "../hooks/useProduct";

let products = ref<Product[]>([]);

onMounted(async () => {
  const response = await getProductPagination(0, "лого");
  products.value = response
});
</script>