<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Product;
use Carbon\Carbon;
use Illuminate\Auth\Events\Validated;
use Illuminate\Support\Facades\Validator;
use Spatie\FlareClient\Http\Exceptions\NotFound;

class ProductController extends Controller
{
    function index()
    {
        return Product::all();
    }

    function show($id)
    {
        $product = Product::find($id);

        if (!$product)
            return response()->json(['message' => 'Product not found'], 404);
        return response()->json(['product' => $product], 200);
    }

    function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'title' => 'required|string|min:1',
            'price' => 'required|integer|min:1',
            'zipcode' => 'required|string',
            'seller' => 'required|string',
            'thumbnailHd' => 'required|string',
            'date' => 'required|date_format:d/m/Y'
        ]);

        if ($validator->fails())
            return response()->json(['message' => $validator->errors()], 422);

        $validated = $validator->validated();
        // formatting to save in the database
        $validated['date'] = Carbon::createFromFormat('d/m/Y', $validated['date'])->format('Y-m-d');

        $product = Product::create($validated);

        return response()->json(['message' => 'Product created', 'product' => $product], 201);
    }

    function update(Request $request, $id)
    {
        $validator = Validator::make($request->all(), [
            'title' => 'nullable|string|min:1',
            'price' => 'nullable|integer|min:1',
            'zipcode' => 'nullable|string',
            'seller' => 'nullable|string',
            'thumbnailHd' => 'nullable|string',
            'date' => 'nullable|date_format:d/m/Y'
        ]);

        if ($validator->fails())
            return response()->json(['message' => $validator->errors()], 422);

        $product = Product::find($id);
        if (!$product)
            return response()->json(['message' => 'Product not found'], 404);

        $validated = $validator->validated();
        // formatting to save in the database
        $validated['date'] = Carbon::createFromFormat('d/m/Y', $validated['date'])->format('Y-m-d');

        $product->update($validated);

        return response()->json(['message' => 'Product updated', 'product' => $product], 200);
    }

    function destroy($id)
    {
        $product = Product::find($id);
        if (!$product)
            return response()->json(['message' => 'Product not found'], 404);

        $product->delete();
        return response()->json(['message' => 'Product deleted'], 204);
    }
}
