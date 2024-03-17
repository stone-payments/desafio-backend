<?php

namespace App\Http\Controllers;

use App\Http\Requests\product\StoreProductRequest;
use App\Http\Requests\product\UpdateProductRequest;
use App\Http\Resources\ErrorResource;
use App\Http\Resources\product\DeletedProductResource;
use App\Http\Resources\product\DeletedResource;
use App\Http\Resources\product\IndexProductResource;
use App\Http\Resources\product\IndexProductResourceCollection;
use App\Http\Resources\product\StoreProductResource;
use App\Http\Resources\product\UpdateProductResource;
use App\Models\Product;
use Carbon\Carbon;

class ProductController extends Controller
{
    function index()
    {
        $users = Product::all();
        return new IndexProductResourceCollection($users);
    }

    function show($id)
    {
        $product = Product::find($id);

        if (!$product)
            return ErrorResource::make(['message' => 'Product not found', 'statusCode' => 404]);
        return IndexProductResource::make($product);
    }

    function store(StoreProductRequest $request)
    {
        $validated = $request->validated();

        // formatting to save in the database
        $validated['date'] = Carbon::createFromFormat('d/m/Y', $validated['date'])->format('Y-m-d');

        $product = Product::create($validated);

        return StoreProductResource::make($product);
    }

    function update(UpdateProductRequest $request, $id)
    {
        $validated = $request->validated();

        $product = Product::find($id);
        if (!$product)
            return ErrorResource::make(['message' => 'Product not found', 'statusCode' => 404]);

        // formatting to save in the database
        $validated['date'] = Carbon::createFromFormat('d/m/Y', $validated['date'])->format('Y-m-d');

        $product->update($validated);

        return UpdateProductResource::make($product);
    }

    function destroy($id)
    {
        $product = Product::find($id);
        if (!$product)
            return ErrorResource::make(['message' => 'Product not found', 'statusCode' => 404]);

        $product->delete();
        return DeletedProductResource::make($product);
    }
}
