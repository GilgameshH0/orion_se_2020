package shilkin;

import shilkin.Exceptions.NoElementsInCollectionException;
import shilkin.Exceptions.NoExistsInCollectionException;
import shilkin.fabrics.AppleGarden;
import shilkin.fabrics.ElectronicFabric;
import shilkin.fabrics.FoodFactory;
import shilkin.interfaces.ShopVisitor;
import shilkin.items.ElectronicItem;
import shilkin.items.FoodItem;
import shilkin.items.ShopItem;
import shilkin.products.Apple;
import shilkin.visitors.ElectronicAddictedVisitor;
import shilkin.visitors.ImJustLookingVisitor;
import shilkin.visitors.RichVisitor;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        Collection<ShopItem> hyperMarket = new ArrayList<>();
        Collection<ElectronicItem> mVideo = new ArrayList<>();
        Collection<FoodItem> spar = new ArrayList<>();
        Collection<Apple> appleMarket = new ArrayList<>();

        ElectronicFabric.fillShopWithElectronicGoods(hyperMarket);
        System.out.println();
        ElectronicFabric.fillShopWithElectronicGoods(mVideo);
        System.out.println();
        AppleGarden.fillShopWithApples(appleMarket);
        System.out.println();
        AppleGarden.fillShopWithApples(hyperMarket);
        System.out.println();
        AppleGarden.fillShopWithApples(spar);
        System.out.println();
        FoodFactory.fillShopWithFood(spar);
        System.out.println();
        FoodFactory.fillShopWithFood(hyperMarket);
        System.out.println();

        ImJustLookingVisitor imJustLookingVisitor = new ImJustLookingVisitor();
        RichVisitor richVisitor = new RichVisitor();
        ElectronicAddictedVisitor electronicAddictedVisitor = new ElectronicAddictedVisitor();
        try {
            System.out.println("В магазины заходит покупатель, который просто смотрит, какой товар есть в магазине.");
            visitShop(imJustLookingVisitor, hyperMarket, mVideo, spar, appleMarket);
            System.out.println("В магазины заходит покупатель, который покупает каждый второй товар в магазине.");
            visitShop(richVisitor, hyperMarket, mVideo, spar, appleMarket);
            System.out.println("В магазины заходит покупатель, который покупает электронный товар с самой большой потребляемой мощностью.");
            visitShop(electronicAddictedVisitor, hyperMarket, mVideo, spar, appleMarket);
        } catch (NoElementsInCollectionException | NoExistsInCollectionException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void visitShop(
            ShopVisitor shopVisitor,
            Collection<ShopItem> hyperMarket,
            Collection<ElectronicItem> mVideo,
            Collection<FoodItem> spar,
            Collection<Apple> appleMarket) throws NoExistsInCollectionException, NoElementsInCollectionException {
        shopVisitor.visitShop(hyperMarket);
        System.out.println();
        shopVisitor.visitShop(mVideo);
        System.out.println();
        shopVisitor.visitShop(spar);
        System.out.println();
        shopVisitor.visitShop(appleMarket);
        System.out.println();
    }
}
